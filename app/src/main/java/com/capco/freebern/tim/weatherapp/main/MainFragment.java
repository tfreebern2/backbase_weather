package com.capco.freebern.tim.weatherapp.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.capco.freebern.tim.weatherapp.R;
import com.capco.freebern.tim.weatherapp.location.LocationListViewFragment;
import com.capco.freebern.tim.weatherapp.map.MapFragment;
import com.capco.freebern.tim.weatherapp.weather.WeatherFragment;

public class MainFragment extends Fragment {

    private LocationListViewFragment mLocationListViewFragment;
    private MapFragment mMapFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,
                container, false);
        ((LinearLayout) view.findViewById(R.id.linearContainer)).setOrientation(getResources().getConfiguration().orientation);

        if (savedInstanceState != null) {
            WeatherFragment weatherFragment = null;
            try {
                weatherFragment = (WeatherFragment) getActivity().getSupportFragmentManager().getFragment(savedInstanceState, "weather");
            } catch(Exception ex){
                ex.printStackTrace();
            }

            if (mLocationListViewFragment == null) {
                mLocationListViewFragment = (LocationListViewFragment) getChildFragmentManager().getFragment(savedInstanceState, "loc");
            }

            if (mMapFragment == null) {
                mMapFragment = (MapFragment) getChildFragmentManager().getFragment(savedInstanceState, "map");
            }

            if(weatherFragment != null && weatherFragment.isAdded()){
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.main_fragment, weatherFragment);
                ft.commit();

            } else {
                if (mLocationListViewFragment.isAdded() && mMapFragment.isAdded()) {
                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_two, mLocationListViewFragment, "loc");
                    transaction.replace(R.id.fragment_one, mMapFragment, "map");
                    transaction.commit();
                }
            }
        } else {
            mLocationListViewFragment = new LocationListViewFragment();
            mMapFragment = new MapFragment();
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_two, mLocationListViewFragment, "loc");
            transaction.replace(R.id.fragment_one, mMapFragment, "map");
            transaction.commit();
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        getChildFragmentManager().putFragment(outState, "map", mMapFragment);
        getChildFragmentManager().putFragment(outState, "loc", mLocationListViewFragment);
        WeatherFragment frag = (WeatherFragment) getActivity().getSupportFragmentManager().findFragmentByTag("weather");
        if(frag != null){
            getActivity().getSupportFragmentManager().putFragment(outState, "weather", frag);
        }
    }

}
