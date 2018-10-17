package com.capco.freebern.tim.weatherapp.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.capco.freebern.tim.weatherapp.LocationsService;
import com.capco.freebern.tim.weatherapp.R;
import com.capco.freebern.tim.weatherapp.help.HelpFragment;

public class MainActivity extends AppCompatActivity {

    private LocationsService mLocationsService;
    private MainFragment mMainFragment;
    private HelpFragment mHelpFragment;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            mMainFragment = (MainFragment) getSupportFragmentManager().getFragment(savedInstanceState, "main");
            mHelpFragment = (HelpFragment) getSupportFragmentManager().getFragment(savedInstanceState, "help");
        } else {
            mMainFragment = new MainFragment();
        }

        mLocationsService = new LocationsService(getApplicationContext());
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();

        if ( mHelpFragment != null && mHelpFragment.isVisible()) {
            transaction.replace(R.id.main_fragment, mHelpFragment, "help");
        } else {
            transaction.replace(R.id.main_fragment, mMainFragment, "main");
        }

        transaction.commit();
        Toolbar tb = findViewById(R.id.main_toolbar);
        setSupportActionBar(tb);
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
                mHelpFragment = new HelpFragment();
                transaction.replace(R.id.main_fragment, mHelpFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "main", mMainFragment);
        if (mHelpFragment != null) {
            getSupportFragmentManager().putFragment(outState, "help", mHelpFragment);
        }
    }
}
