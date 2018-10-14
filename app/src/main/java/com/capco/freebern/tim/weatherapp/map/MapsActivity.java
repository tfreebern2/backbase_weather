package com.capco.freebern.tim.weatherapp.map;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.capco.freebern.tim.weatherapp.R;
import com.capco.freebern.tim.weatherapp.location.model.Location;
import com.capco.freebern.tim.weatherapp.map.marker.MarkerManager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("locations");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                LatLng newLocation = new LatLng(
                        dataSnapshot.child("latitude").getValue(Double.class),
                        dataSnapshot.child("longitude").getValue(Double.class)
                );
                mMap.addMarker(new MarkerOptions()
                        .position(newLocation)
                        .title(dataSnapshot.child("name").getValue(String.class)));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions marker = new MarkerOptions().position
                        (new LatLng(latLng.latitude, latLng.longitude)).title("New Location");

                Location location = MarkerManager.convertToLocation(marker);
                mDatabaseReference.push().setValue(location);
            }
        });

    }

}