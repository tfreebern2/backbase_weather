package com.capco.freebern.tim.weatherapp.location;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.capco.freebern.tim.weatherapp.R;
import com.capco.freebern.tim.weatherapp.map.MapsActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LocationActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseReference;
    private ListView mLocationListView;
    private LocationListAdapter mAdapter;
    private ImageButton mMapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mLocationListView = (ListView) findViewById(R.id.location_list_view);
        mMapButton = (ImageButton) findViewById(R.id.mapButton);

        mMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapIntent = new Intent(LocationActivity.this, MapsActivity.class);
                startActivity(mapIntent);
            }
        });
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
