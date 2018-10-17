package com.capco.freebern.tim.weatherapp.main;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toolbar;

import com.capco.freebern.tim.weatherapp.R;
import com.capco.freebern.tim.weatherapp.location.LocationListViewFragment;
import com.capco.freebern.tim.weatherapp.LocationsService;
import com.capco.freebern.tim.weatherapp.map.MapFragment;

public class MainActivity extends AppCompatActivity {

    private ListView mLocationListView;
    private LocationsService mLocationsService;
    private LocationListViewFragment mLocationListViewFragment;
    private MapFragment mMapFragment;
    private MainFragment mMainFragment;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
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
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public LocationsService getLocationsService() {
        return mLocationsService;
    }

}
