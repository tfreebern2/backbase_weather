package com.capco.freebern.tim.weatherapp;

import android.content.Context;
import android.content.SharedPreferences;

import com.capco.freebern.tim.weatherapp.location.model.Location;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LocationsService {

    private static final String SHARED_PREFERENCES_NAME = "com.capco.freebern.tim.weatherapp.locations";

    private SharedPreferences sharedPreferences;

    private Location getLocation(String json){
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        return gson.fromJson(json, Location.class);
    }
    public LocationsService(Context context){
        this.sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public void saveLocation(String key, Location location){
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        String json = gson.toJson(location);
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putString(key, json);
        editor.apply();
    }

    public void removeLocation(String key){
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
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

}
