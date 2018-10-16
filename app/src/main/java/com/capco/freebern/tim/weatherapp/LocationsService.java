package com.capco.freebern.tim.weatherapp;

import android.content.Context;
import android.content.SharedPreferences;

import com.capco.freebern.tim.weatherapp.location.model.Location;
import com.capco.freebern.tim.weatherapp.weather.GeocodeResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LocationsService {

    private static final String SHARED_PREFERENCES_NAME = "com.capco.freebern.tim.weatherapp.locations";

    private SharedPreferences sharedPreferences;
    private List<LocationsUpdatedListener> listeners;

    private Location getLocation(String json){
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        return gson.fromJson(json, Location.class);
    }
    public LocationsService(Context context){
        this.sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        this.listeners = new ArrayList<>();
    }

    public void saveLocation(String key, Location location){
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        String json = gson.toJson(location);
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putString(key, json);
        editor.apply();
        updateListeners(getAllLocations());
    }

    public void removeLocation(String key){
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
        updateListeners(getAllLocations());
    }

    public List<Location> getAllLocations(){
        Map<String, String> map = (Map<String, String>)this.sharedPreferences.getAll();
        List<String> locationStrs = new ArrayList<>(map.values());
        List<Location> locations = new ArrayList<>();
        for(String locationStr : locationStrs){
            locations.add(getLocation(locationStr));
        }
        return locations;
    }

    public String getCityName(String json){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        GeocodeResponse geocodeResponse = gson.fromJson(json, GeocodeResponse.class);
        if(geocodeResponse.results.length > 0)
            return geocodeResponse.results[0].formattedAddress;
        else
            return "Unknown Location";
    }

    public void registerListener(LocationsUpdatedListener listener) {
        this.listeners.add(listener);
    }

    public void unregisterListener(LocationsUpdatedListener listener) {
        this.listeners.remove(listener);
    }

    private void updateListeners(List<Location> locations) {
        for (LocationsUpdatedListener listener : listeners) {
            listener.Updated(locations);
        }
    }

}
