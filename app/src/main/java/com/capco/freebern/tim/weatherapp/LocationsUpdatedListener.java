package com.capco.freebern.tim.weatherapp;

import com.capco.freebern.tim.weatherapp.location.model.Location;

import java.util.List;

public interface LocationsUpdatedListener {
    void Updated(List<Location> locations);
}
