package com.capco.freebern.tim.weatherapp.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.capco.freebern.tim.weatherapp.LocationsService;
import com.capco.freebern.tim.weatherapp.R;
import com.capco.freebern.tim.weatherapp.help.HelpFragment;

public class MainActivity extends AppCompatActivity {

    private LocationsService mLocationsService;
    private MainFragment mMainFragment;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLocationsService = new LocationsService(getApplicationContext());

        mMainFragment = new MainFragment();

        final FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.main_fragment, mMainFragment);

        transaction.commit();

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public LocationsService getLocationsService() {
        return mLocationsService;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.miHelp:
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                HelpFragment helpFragment = new HelpFragment();
                transaction.replace(R.id.main_fragment, helpFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
