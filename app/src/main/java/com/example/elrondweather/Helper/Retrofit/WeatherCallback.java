package com.example.elrondweather.Helper.Retrofit;

import com.example.elrondweather.Helper.WeatherClasses.JsonToJava.Weather;

public interface WeatherCallback{
    void onSuccess(Weather Weather);
    void onFailure(Throwable throwable);
}