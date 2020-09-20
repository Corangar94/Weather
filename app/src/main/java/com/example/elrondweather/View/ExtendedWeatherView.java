package com.example.elrondweather.View;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.elrondweather.Helper.WeatherClasses.JsonToJava.Weather;
import com.example.elrondweather.Helper.WeatherClasses.WeatherForecast;
import com.example.elrondweather.R;

import java.util.Objects;

public class ExtendedWeatherView extends AppCompatActivity {
    int index;
    Weather weather;
    TextView temperature, rainChance, windSpeed, humidity, uvIndex, sunrise, sunset;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.extended_weather_view);
        index = Objects.requireNonNull(getIntent().getExtras()).getInt("SELECTED_DAY");
        weather = WeatherForecast.getWeather();
        temperature = findViewById(R.id.temperature);
        rainChance = findViewById(R.id.rainChance);
        windSpeed = findViewById(R.id.windSpeed);
        humidity = findViewById(R.id.humidity);
        uvIndex = findViewById(R.id.uvIndex);
        sunrise = findViewById(R.id.sunrise);
        sunset = findViewById(R.id.sunset);

        temperature.setText(String.valueOf(Math.round(weather.getDaily().get(index).getTemp().getDay())));
        rainChance.setText(String.valueOf(Math.round(weather.getDaily().get(index).getPop())));
        windSpeed.setText(String.valueOf(Math.round(weather.getDaily().get(index).getWindSpeed())));
        humidity.setText(String.valueOf(Math.round(weather.getDaily().get(index).getHumidity())));
        uvIndex.setText(String.valueOf(Math.round(weather.getDaily().get(index).getUvi())));
        sunrise.setText(String.valueOf((weather.getDaily().get(index).getSunrise())));
        sunset.setText(String.valueOf((weather.getDaily().get(index).getSunset())));

    }
}
