package com.capco.freebern.tim.weatherapp;

import com.capco.freebern.tim.weatherapp.location.model.Location;

public interface ApiFinishedListener {
    void onFinished(Location location);
}
