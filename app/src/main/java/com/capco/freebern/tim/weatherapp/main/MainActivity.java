package com.capco.freebern.tim.weatherapp.main;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.capco.freebern.tim.weatherapp.R;
import com.capco.freebern.tim.weatherapp.location.LocationListViewFragment;
import com.capco.freebern.tim.weatherapp.location.LocationsService;
import com.capco.freebern.tim.weatherapp.map.MapFragment;

public class MainActivity extends FragmentActivity implements LocationListViewFragment.OnLineSelectedListener {

    private ListView mLocationListView;
    private LocationsService mLocationsService;
    private LocationListViewFragment mLocationListViewFragment;
    private MapFragment mMapFragment;
    private MainFragment mMainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLocationsService = new LocationsService(getApplicationContext());

        mMainFragment = new MainFragment();

        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.main_fragment, mMainFragment);

        transaction.commit();

    }

    @Override
    public void onListItemSelected(int position) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public LocationsService getLocationsService() {
        return mLocationsService;
    }

}
