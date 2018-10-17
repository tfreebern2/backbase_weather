package com.capco.freebern.tim.weatherapp.weather;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.capco.freebern.tim.weatherapp.R;
import com.capco.freebern.tim.weatherapp.WeatherService;
import com.capco.freebern.tim.weatherapp.location.model.Location;
import com.capco.freebern.tim.weatherapp.weather.model.CurrentWeather;

public class WeatherFragment extends Fragment {
    private WeatherService weatherService;
    private Location location;
    private View view;
    private Activity mActivity;
    private final String LOCATION_KEY = "locationKey";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null && location == null) {
            location = (Location) savedInstanceState.getSerializable(LOCATION_KEY);
        }
        view = inflater.inflate(R.layout.fragment_weather, null);
        weatherService = new WeatherService();
        OpenWeatherAPITask openWeatherAPITask = new OpenWeatherAPITask(getString(R.string.open_weather_key), this);
        openWeatherAPITask.execute(location);
        TextView cityNameText = view.findViewById(R.id.tv_city);
        cityNameText.setText(location.getName());

        return view;
    }

    public WeatherService getWeatherService(){
        return weatherService;
    }

    public void updateView(CurrentWeather currentWeather){
        setTextView(R.id.tv_temperature, currentWeather.getTemperature());
        setTextView(R.id.tv_humidity, currentWeather.getHumidity());
        setTextView(R.id.tv_rain_volume, currentWeather.getRainVolume());
        setTextView(R.id.tv_cloudiness, currentWeather.getCloudiness());
        setTextView(R.id.tv_wind_speed, currentWeather.getWindSpeed());
        setTextView(R.id.tv_wind_direction, currentWeather.getWindDirection());
    }

    private void setTextView(int id, double val){
        TextView tv = view.findViewById(id);
        tv.setText(String.valueOf(val));
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(LOCATION_KEY, location);
    }

}
