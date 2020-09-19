package com.example.elrondweather.Helper.WeatherClasses;


import com.example.elrondweather.Helper.WeatherClasses.JsonToJava.Weather;

public interface WeatherForecastCallback{
    void onSuccess(Weather weather);
    void onFailure(Throwable throwable);
}