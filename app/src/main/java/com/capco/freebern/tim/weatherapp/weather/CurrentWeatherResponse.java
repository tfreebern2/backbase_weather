package com.capco.freebern.tim.weatherapp.weather;

import com.google.gson.annotations.SerializedName;

public class CurrentWeatherResponse {
    public Coord coord;
    public Weather[] weather;
    public String base;
    public Main main;
    public Wind wind;
    public Cloud clouds;
    public Rain rain;
    public Snow snow;
    public long dt;
    public Sys sys;
    public long id;
    public String name;
    public int cod;

    public class Coord {
        public double lon;
        public double lat;
    }

    public class Weather {
        public long id;
        public String main;
        public String description;
        public String icon;
    }

    public class Main {
        public double temp;
        public double pressure;
        public double humidity;
        @SerializedName("temp_min")
        public double tempMin;
        @SerializedName("temp_max")
        public double tempMax;
        @SerializedName("sea_level")
        public double seaLevel;
        @SerializedName("grnd_level")
        public double groundLevel;
    }

    public class Wind {
        public double speed;
        @SerializedName("deg")
        public double direction;
    }

    public class Cloud {
        public double all;
    }

    public class Rain {
        @SerializedName("3h")
        public double threeHourVolume;
    }

    public class Snow {
        @SerializedName("3h")
        public double threeHourVolume;
    }

    public class Sys {
        public int type;
        public long id;
        public double message;
        @SerializedName("country")
        public String countryCode;
        public long sunrise;
        public long sunset;
    }
}