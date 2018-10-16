package com.capco.freebern.tim.weatherapp.weather;

import android.os.AsyncTask;
import android.util.Log;

import com.capco.freebern.tim.weatherapp.ApiFinishedListener;
import com.capco.freebern.tim.weatherapp.LocationsService;
import com.capco.freebern.tim.weatherapp.location.model.Location;
import com.google.android.gms.maps.model.Marker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

public class GeocodeAPITask extends AsyncTask<Location, Void, String> {
    private static final String API = "https://maps.googleapis.com/maps/api/geocode/json?latlng=%f,%f&key=%s";
    private static final String TAG = "GeocodeAPITask";
    private String apiKey;
    private Location location;
    private Marker marker;
    private LocationsService locationsService;
    private ApiFinishedListener mApiFinishedListener;
    private URL url;

    public GeocodeAPITask(String apiKey, Location location, LocationsService locationsService, ApiFinishedListener listener) {
        this.apiKey = apiKey;
        this.location = location;
        this.locationsService = locationsService;
        this.mApiFinishedListener = listener;
    }

    @Override
    protected String doInBackground(Location... locations) {
        String apiString = String.format(Locale.US, API, locations[0].getLatitude(), locations[0].getLongitude(), apiKey);
        Log.d("URL", apiString);
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
                    Log.d("JSON", stringBuilder.toString());
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
        String cityName = locationsService.getCityName(s);
        this.location.setName(cityName);
        mApiFinishedListener.onFinished(this.location);
        super.onPostExecute(s);
    }

}
