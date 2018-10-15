package com.capco.freebern.tim.weatherapp.map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;

import com.capco.freebern.tim.weatherapp.LocationsService;
import com.capco.freebern.tim.weatherapp.R;
import com.capco.freebern.tim.weatherapp.location.LocationActivity;
import com.capco.freebern.tim.weatherapp.location.model.Location;
import com.capco.freebern.tim.weatherapp.map.marker.MarkerManager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.UUID;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ImageButton mLocationsButton;
    private LocationsService mLocationsService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mLocationsService = new LocationsService(getApplicationContext());
        mLocationsButton = (ImageButton) findViewById(R.id.locationsButton);

        mLocationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent locationsIntent = new Intent(MapsActivity.this, LocationActivity.class);
                startActivity(locationsIntent);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions markerOpts = new MarkerOptions().position
                        (new LatLng(latLng.latitude, latLng.longitude)).title(UUID.randomUUID().toString());

                Marker marker = mMap.addMarker(markerOpts);
                Location location = MarkerManager.convertToLocation(marker);
                getLocationsService().saveLocation(location.getName(), location);
            }
        });

        loadLocations();
    }

    private void loadLocations(){
        List<Location> locations = getLocationsService().getAllLocations();
        for(Location location : locations){
            mMap.addMarker(MarkerManager.convertToMarkerOptions(location));
        }
    }

    public LocationsService getLocationsService(){
        return mLocationsService;
    }

}