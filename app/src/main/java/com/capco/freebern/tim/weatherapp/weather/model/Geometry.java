package com.capco.freebern.tim.weatherapp.weather.model;

import android.graphics.Point;

import com.google.gson.annotations.SerializedName;

public class Geometry {
    public Area bounds;
    public Point location;
    @SerializedName("location_type")
    public String locationType;
    public Area viewport;
}

