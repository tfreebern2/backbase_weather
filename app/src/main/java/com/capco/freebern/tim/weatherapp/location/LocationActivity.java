package com.capco.freebern.tim.weatherapp.location;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.capco.freebern.tim.weatherapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LocationActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseReference;
    private ListView mLocationListView;
    private LocationListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mLocationListView = (ListView) findViewById(R.id.location_list_view);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter = new LocationListAdapter(this, mDatabaseReference);
        mLocationListView.setAdapter(mAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        mAdapter.cleanUp();
    }
}
