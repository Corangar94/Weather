package com.example.elrondweather.Helper.WeatherClasses;

import com.example.elrondweather.Helper.WeatherClasses.JsonToJava.Weather;

public  class WeatherForecast {
    private static Weather weather;

    public static void setWeather(Weather weather) {
        WeatherForecast.weather = weather;
    }
    public static Weather getWeather(){
        return weather;
    }
}
