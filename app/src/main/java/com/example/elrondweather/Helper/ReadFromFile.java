package com.example.elrondweather.Helper;


import com.example.elrondweather.Helper.WeatherClasses.JsonToJava.Weather;
import com.preference.PowerPreference;

import java.io.Serializable;


public class ReadFromFile implements Serializable {

    public static void saveObject(Weather weather) {
        PowerPreference.getDefaultFile().putObject("weather", weather);

    }

    public static Weather getObject() {
        return PowerPreference.getDefaultFile()
                .getObject("weather", Weather.class);
    }
}