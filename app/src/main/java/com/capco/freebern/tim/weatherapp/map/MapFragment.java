package com.capco.freebern.tim.weatherapp.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.capco.freebern.tim.weatherapp.ApiFinishedListener;
import com.capco.freebern.tim.weatherapp.LocationsService;
import com.capco.freebern.tim.weatherapp.LocationsUpdatedListener;
import com.capco.freebern.tim.weatherapp.R;
import com.capco.freebern.tim.weatherapp.location.model.Location;
import com.capco.freebern.tim.weatherapp.main.MainActivity;
import com.capco.freebern.tim.weatherapp.map.marker.MarkerManager;
import com.capco.freebern.tim.weatherapp.weather.GeocodeAPITask;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback, LocationsUpdatedListener, ApiFinishedListener {

    private GoogleMap mMap;
    private LocationsService mLocationsService;
    private ApiFinishedListener mApiFinishedListener;
    private GeocodeAPITask mGeocodeAPITask;

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
        ((MainActivity) getActivity()).getLocationsService().registerListener(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        final ApiFinishedListener listener = this;

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Location location = new Location();
                location.setLatitude(latLng.latitude);
                location.setLongitude(latLng.longitude);
                GeocodeAPITask geocodeAPITask = new GeocodeAPITask(getString(R.string.google_maps_key), location, mLocationsService, listener);
                geocodeAPITask.execute(location);

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
    public void onDestroyView() {
        ((MainActivity) getActivity()).getLocationsService().unregisterListener(this);
        super.onDestroyView();
    }

    @Override
    public void Updated(List<Location> locations) {
        mMap.clear();
        loadLocations();
    }

    @Override
    public void onFinished(Location location) {

        ((MainActivity) getActivity()).getLocationsService().saveLocation(location.getName(), location);
    }
}