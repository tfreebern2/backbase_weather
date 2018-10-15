package com.capco.freebern.tim.weatherapp.location;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.capco.freebern.tim.weatherapp.R;
import com.capco.freebern.tim.weatherapp.main.MainActivity;

public class LocationListViewFragment extends ListFragment {
    OnLineSelectedListener mCallback;
    ListAdapter mAdapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnLineSelectedListener)activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnLineSelectedListener");
        }
    }

    // Container Activity must implement this interface
    public interface OnLineSelectedListener {
        public void onListItemSelected(int position);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_locations, null);
        mAdapter = new LocationListAdapter(getActivity(), ((MainActivity) getActivity()).getLocationsService().getAllLocations());
        ListView listView = view.findViewById(R.id.list);
        listView.setAdapter(mAdapter);

        return view;
    }
}
