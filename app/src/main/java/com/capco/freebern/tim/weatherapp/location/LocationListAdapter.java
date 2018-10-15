package com.capco.freebern.tim.weatherapp.location;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capco.freebern.tim.weatherapp.R;
import com.capco.freebern.tim.weatherapp.location.model.Location;
import java.util.List;

public class LocationListAdapter extends BaseAdapter {

    private Activity mActivity;
    private List<Location> mLocations;

    public LocationListAdapter(Activity activity, List<Location> locations) {
        mActivity = activity;
        mLocations = locations;
    }

    static class ViewHolder{
        TextView locationName;
        LinearLayout.LayoutParams params;
    }

    @Override
    public int getCount() {
        return mLocations.size();
    }

    @Override
    public Location getItem(int position) {
        return mLocations.get(position);
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


}
