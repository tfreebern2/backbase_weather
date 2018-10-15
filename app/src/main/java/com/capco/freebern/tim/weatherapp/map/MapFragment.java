package com.capco.freebern.tim.weatherapp.map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.capco.freebern.tim.weatherapp.location.LocationsService;
import com.capco.freebern.tim.weatherapp.R;
import com.capco.freebern.tim.weatherapp.main.MainActivity;
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

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationsService mLocationsService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationsService = new LocationsService(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map,
                container, false);

        initialize();

        return view;
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
                ((MainActivity) getActivity()).getLocationsService().saveLocation(location.getName(), location);
            }
        });

        loadLocations();
    }

    private void loadLocations(){
        List<Location> locations = ((MainActivity) getActivity()).getLocationsService().getAllLocations();
        for(Location location : locations){
            mMap.addMarker(MarkerManager.convertToMarkerOptions(location));
        }
    }


    public void initialize() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

}