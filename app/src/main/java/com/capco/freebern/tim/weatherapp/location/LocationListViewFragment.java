package com.capco.freebern.tim.weatherapp.location;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.capco.freebern.tim.weatherapp.R;
import com.capco.freebern.tim.weatherapp.location.model.Location;
import com.capco.freebern.tim.weatherapp.main.MainActivity;
import com.capco.freebern.tim.weatherapp.weather.WeatherFragment;

public class LocationListViewFragment extends Fragment {
    LocationListAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_locations, null);

        mAdapter = new LocationListAdapter(getActivity(), ((MainActivity) getActivity()).getLocationsService().getAllLocations());
        ((MainActivity) getActivity()).getLocationsService().registerListener(mAdapter);
        ListView listView = view.findViewById(R.id.list);
        listView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        ((MainActivity) getActivity()).getLocationsService().unregisterListener(mAdapter);
        super.onDestroyView();
    }
}
