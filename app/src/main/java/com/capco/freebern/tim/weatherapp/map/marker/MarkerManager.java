package com.capco.freebern.tim.weatherapp.map.marker;

import com.capco.freebern.tim.weatherapp.location.model.Location;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MarkerManager {

    public static Location convertToLocation(Marker marker) {
        Location location = new Location();
        location.setName(marker.getTitle());
        location.setLatitude(marker.getPosition().latitude);
        location.setLongitude(marker.getPosition().longitude);
        return location;
    }

    public static MarkerOptions convertToMarkerOptions(Location location){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(location.getLatitude(), location.getLongitude()));
        markerOptions.title(location.getName());
        return markerOptions;
    }
}
