package com.capco.freebern.tim.weatherapp;

import com.capco.freebern.tim.weatherapp.weather.CurrentWeatherResponse;
import com.capco.freebern.tim.weatherapp.weather.model.CurrentWeather;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WeatherService {
    public CurrentWeather getCurrentWeather(String json){
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        CurrentWeatherResponse currentWeatherResponse = gson.fromJson(json, CurrentWeatherResponse.class);
        CurrentWeather currentWeather = new CurrentWeather();
        currentWeather.setTemperature(currentWeatherResponse.main.temp);
        currentWeather.setHumidity(currentWeatherResponse.main.humidity);
        double rainVol = currentWeatherResponse.rain != null ? currentWeatherResponse.rain.threeHourVolume : 0;
        currentWeather.setRainVolume(rainVol);
        double cloudiness = currentWeatherResponse.clouds != null ? currentWeatherResponse.clouds.all : 0;
        currentWeather.setCloudiness(cloudiness);
        currentWeather.setWindSpeed(currentWeatherResponse.wind.speed);
        currentWeather.setWindDirection(currentWeatherResponse.wind.direction);
        return currentWeather;
    }

}
