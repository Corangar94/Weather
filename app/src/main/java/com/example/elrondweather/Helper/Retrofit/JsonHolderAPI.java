package com.example.elrondweather.Helper.Retrofit;

import com.example.elrondweather.Helper.WeatherClasses.JsonToJava.Weather;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface JsonHolderAPI {
    String FORECAST = "/data/2.5/onecall";
    @GET(FORECAST)
    Call<Weather> getCurrentWeatherByCityName(
            @QueryMap(encoded=true) Map<String,String> options );
}
