package com.capco.freebern.tim.weatherapp.location;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capco.freebern.tim.weatherapp.R;
import com.capco.freebern.tim.weatherapp.location.model.Location;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class LocationListAdapter extends BaseAdapter {

    private Activity mActivity;
    private DatabaseReference mDatabaseReference;
    private ArrayList<DataSnapshot> mSnapshotList;

    public ChildEventListener mListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            mSnapshotList.add(dataSnapshot);
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    public LocationListAdapter(Activity activity, DatabaseReference ref) {
        mActivity = activity;
        mDatabaseReference = ref.child("locations");
        mDatabaseReference.addChildEventListener(mListener);

        mSnapshotList = new ArrayList<>();
    }

    static class ViewHolder {
        TextView locationName;
        LinearLayout.LayoutParams params;
    }

    @Override
    public int getCount() {
        return mSnapshotList.size();
    }

    @Override
    public Location getItem(int position) {
        DataSnapshot dataSnapshot = mSnapshotList.get(position);
        return dataSnapshot.getValue(Location.class);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.location_row, parent, false);

            final ViewHolder holder = new ViewHolder();
            holder.locationName = (TextView) convertView.findViewById(R.id.location);
            holder.params = (LinearLayout.LayoutParams) holder.locationName.getLayoutParams();
            convertView.setTag(holder);
        }

        final Location location = getItem(position);
        final ViewHolder holder = (ViewHolder) convertView.getTag();

        String name = location.getName();
        holder.locationName.setText(name);

        return convertView;
    }

    public void cleanUp() {
        mDatabaseReference.removeEventListener(mListener);
    }
}
