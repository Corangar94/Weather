package com.example.elrondweather;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.elrondweather.Helper.WeatherClasses.JsonToJava.Weather;
import com.example.elrondweather.Helper.WeatherClasses.WeatherForecast;
import com.example.elrondweather.View.WeatherDailyView;
import com.example.elrondweather.View.WeatherItemView;
import com.kwabenaberko.openweathermaplib.models.threehourforecast.ThreeHourForecast;


public class MainActivity extends AppCompatActivity {
    SplashScreen splashScreen;
    Context context;
    static ThreeHourForecast forecast;
    Weather weather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        splashScreen = new SplashScreen();
        LayoutInflater layoutInflater = getLayoutInflater();
        View parentView = this.findViewById(android.R.id.content).getRootView();


        if (WeatherForecast.getWeather() != null) {
            weather = WeatherForecast.getWeather();
            setViews();
        }
    }

    private void setViews() {
        WeatherItemView weatherItemView = findViewById(R.id.weatherView);
        weatherItemView.setWeatherItem(weather);
        WeatherDailyView dailyItem = findViewById(R.id.weatherDailyView1);
        WeatherDailyView dailyItem2 = findViewById(R.id.weatherDailyView2);
        WeatherDailyView dailyItem3 = findViewById(R.id.weatherDailyView3);
        WeatherDailyView dailyItem4 = findViewById(R.id.weatherDailyView4);
        WeatherDailyView dailyItem5 = findViewById(R.id.weatherDailyView5);
        WeatherDailyView dailyItem6 = findViewById(R.id.weatherDailyView6);
        dailyItem.isClickable();
        dailyItem.setDailyItem(weather, 1);
        dailyItem2.setDailyItem(weather, 2);
        dailyItem3.setDailyItem(weather, 3);
        dailyItem4.setDailyItem(weather, 4);
        dailyItem5.setDailyItem(weather, 5);
        dailyItem6.setDailyItem(weather, 6);
    }

}
