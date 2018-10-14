package com.capco.freebern.tim.weatherapp.map;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.capco.freebern.tim.weatherapp.R;
import com.capco.freebern.tim.weatherapp.location.model.Location;
import com.capco.freebern.tim.weatherapp.map.marker.MarkerManager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions marker = new MarkerOptions().position
                        (new LatLng(latLng.latitude, latLng.longitude)).title("New Location");

                mMap.addMarker(marker);
                Location location = MarkerManager.convertToLocation(marker);
                mDatabaseReference.child("locations").push().setValue(location);
            }
        });

    }
}