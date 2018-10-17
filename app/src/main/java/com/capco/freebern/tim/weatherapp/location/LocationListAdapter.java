package com.capco.freebern.tim.weatherapp.location;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capco.freebern.tim.weatherapp.LocationsService;
import com.capco.freebern.tim.weatherapp.LocationsUpdatedListener;
import com.capco.freebern.tim.weatherapp.R;
import com.capco.freebern.tim.weatherapp.location.model.Location;
import com.capco.freebern.tim.weatherapp.main.MainActivity;
import com.capco.freebern.tim.weatherapp.weather.WeatherFragment;

import java.util.List;

public class LocationListAdapter extends BaseAdapter implements LocationsUpdatedListener {

    private Activity mActivity;
    private List<Location> mLocations;

    public LocationListAdapter(Activity activity, List<Location> locations) {
        mActivity = activity;
        mLocations = locations;
    }

    @Override
    public void Updated(List<Location> locations) {
        mLocations = locations;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        TextView locationName;
        ImageButton removeLocation;
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
            holder.removeLocation = (ImageButton) convertView.findViewById(R.id.remove_location);
            holder.params = (LinearLayout.LayoutParams) holder.locationName.getLayoutParams();
            convertView.setTag(holder);
        }

        final Location location = getItem(position);
        final ViewHolder holder = (ViewHolder) convertView.getTag();

        final String name = location.getName();
        holder.locationName.setText(name);

        holder.locationName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = ((FragmentActivity) mActivity)
                        .getSupportFragmentManager().beginTransaction();
                WeatherFragment weatherFragment = new WeatherFragment();
                weatherFragment.setLocation(location);
                transaction.replace(R.id.main_fragment, weatherFragment, "weather");
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        holder.removeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) view.getContext()).getLocationsService().removeLocation(name);
                notifyDataSetChanged();
            }
        });

        return convertView;

    }


}
