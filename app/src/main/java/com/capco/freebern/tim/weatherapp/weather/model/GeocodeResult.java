package com.capco.freebern.tim.weatherapp.weather.model;

import com.google.gson.annotations.SerializedName;

public class GeocodeResult {
    @SerializedName("address_components")
    public AddressComponent[] addressComponents;
    @SerializedName("formatted_address")
    public String formattedAddress;
    public Geometry geometry;
    @SerializedName("place_id")
    public String placeId;
    public String[] types;
}
