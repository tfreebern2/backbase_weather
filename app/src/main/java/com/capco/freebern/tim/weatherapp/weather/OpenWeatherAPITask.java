package com.capco.freebern.tim.weatherapp.weather;

import android.os.AsyncTask;
import android.util.Log;

import com.capco.freebern.tim.weatherapp.location.model.Location;
import com.capco.freebern.tim.weatherapp.weather.model.CurrentWeather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

public class OpenWeatherAPITask extends AsyncTask<Location, Void, String> {
    private static final String API = "http://api.openweathermap.org/data/2.5/weather?lat=%1$f&lon=%2$f&appid=%3$s";
    private static final String TAG = "OpenWeatherAPITask";
    private String apiKey;
    private WeatherFragment weatherFragment;
    private URL url;

    public OpenWeatherAPITask(String apiKey, WeatherFragment weatherFragment) {
        this.apiKey = apiKey;
        this.weatherFragment = weatherFragment;
    }

    @Override
    protected String doInBackground(Location... locations) {
        String apiString = String.format(Locale.US, API, locations[0].getLatitude(), locations[0].getLongitude(), apiKey);
        try {
            url = new URL(apiString);
            try {
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                finally{
                    urlConnection.disconnect();
                }
            } catch (IOException e) {
                Log.e(TAG, "Error connecting to Open Weather API", e);
            }
        } catch(MalformedURLException e){
            Log.e(TAG, "URL is malformed: " + apiString, e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        CurrentWeather currentWeather = weatherFragment.getWeatherService().getCurrentWeather(s);
        weatherFragment.updateView(currentWeather);
        super.onPostExecute(s);
    }
}
