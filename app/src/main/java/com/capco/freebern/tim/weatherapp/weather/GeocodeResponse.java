package com.capco.freebern.tim.weatherapp.weather;

import com.google.gson.annotations.SerializedName;

public class GeocodeResponse {
    @SerializedName("plus_code")
    public PlusCode plusCode;

    public GeocodeResult[] results;

    public String status;

    public class PlusCode {
        @SerializedName("compound_code")
        public String compoundCode;
        @SerializedName("global_code")
        public String globalCode;
    }
    public class GeocodeResult {
        @SerializedName("address_components")
        public AddressComponent[] addressComponents;
        @SerializedName("formatted_address")
        public String formattedAddress;
        public Geometry geometry;
        @SerializedName("place_id")
        public String placeId;
        public String[] types;

        public class AddressComponent {
            @SerializedName("long_name")
            public String longName;
            @SerializedName("short_name")
            public String shortName;
            public String[] types;
        }
        public class Geometry {
            public Area bounds;
            public Point location;
            @SerializedName("location_type")
            public String locationType;
            public Area viewport;
            public class Area {
                public Point northeast;
                public Point southwest;
            }
            public class Point {
                public double lat;
                public double lng;
            }
        }
    }
}
