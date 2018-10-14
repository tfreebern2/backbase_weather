package com.capco.freebern.tim.weatherapp.map.marker;

import com.capco.freebern.tim.weatherapp.location.model.Location;
import com.google.android.gms.maps.model.MarkerOptions;

public class MarkerManager {

    public static Location convertToLocation(MarkerOptions marker) {
        Location location = new Location();
        location.setName(marker.getTitle());
        location.setLatitude(marker.getPosition().latitude);
        location.setLongitude(marker.getPosition().longitude);
        return location;
    }
}
