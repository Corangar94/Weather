package com.example.elrondweather;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.elrondweather.Helper.CurrentLocation;
import com.example.elrondweather.Helper.WeatherClasses.JsonToJava.Weather;
import com.example.elrondweather.Helper.WeatherClasses.WeatherForecast;
import com.kwabenaberko.openweathermaplib.constants.Lang;
import com.kwabenaberko.openweathermaplib.constants.Units;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.implementation.callbacks.ThreeHourForecastCallback;
import com.kwabenaberko.openweathermaplib.models.threehourforecast.ThreeHourForecast;

import static android.content.ContentValues.TAG;


public class MainActivity extends AppCompatActivity {
    SplashScreen splashScreen;
    Context context;
    static ThreeHourForecast forecast;
    Weather weather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = getApplicationContext();
        splashScreen = new SplashScreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(WeatherForecast.getWeather()!=null){
            //set title
            weather=WeatherForecast.getWeather();
            addViews();
            setViews();
            Log.e("ar fi bine","asd");
        }

    }

    private void addViews() {
        TextView locationTextView = findViewById(R.id.locationName);
        locationTextView.setText(CurrentLocation.getLocationName());
        ConstraintLayout item = findViewById(R.id.mainActivity);
        View topPanel = getLayoutInflater().inflate(R.layout.weather_item, null);
        View child2 = getLayoutInflater().inflate(R.layout.daily_weather_item, null);
        View child3 = getLayoutInflater().inflate(R.layout.daily_weather_item, null);
        View child4 = getLayoutInflater().inflate(R.layout.daily_weather_item, null);
        View child5 = getLayoutInflater().inflate(R.layout.daily_weather_item, null);
        View child6 = getLayoutInflater().inflate(R.layout.daily_weather_item, null);
        View child7 = getLayoutInflater().inflate(R.layout.daily_weather_item, null);
        item.addView(topPanel);
        item.addView(child2);
        item.addView(child3);
        item.addView(child4);
        item.addView(child5);
        item.addView(child6);
        item.addView(child7);
    }
    private void setViews(){
        TextView titleTemp = findViewById(R.id.temperature);
        TextView titleRain = findViewById(R.id.rainChance);
        TextView humidityTitle = findViewById(R.id.humidity);
        titleTemp.setText((String.valueOf(weather.getDaily().get(0).getTemp().getDay())));
        titleRain.setText((String.valueOf(weather.getDaily().get(0).getPop())));
        humidityTitle.setText((String.valueOf(weather.getDaily().get(0).getHumidity())));
    }

    public void setTextViews() {
        Log.e("2", String.valueOf(forecast));
        TextView temperatureTextView = findViewById(R.id.temperature);
        TextView humidityTextView = findViewById(R.id.humidity);
        TextView rainTextView = findViewById(R.id.rainChance);
        temperatureTextView.setText(String.valueOf(Math.round(forecast.getList().get(0).getMain().getTemp())));
        humidityTextView.setText(String.format("%s%%", (Math.round(forecast.getList().get(0).getMain().getHumidity()))));
        rainTextView.setText(String.format("%s%%", (forecast.getList().get(0).getMain().getTempMax())));

    }

    public void toJSON() {
        OpenWeatherMapHelper helper = new OpenWeatherMapHelper("8e33ed387f59ba576a91cb7859e8366e");
        helper.setLang(Lang.ENGLISH);
        helper.setUnits(Units.METRIC);
        helper.getThreeHourForecastByCityName(CurrentLocation.getLocationName(), new ThreeHourForecastCallback() {
            @Override
            public void onSuccess(ThreeHourForecast threeHourForecast) {
                Log.v(TAG, "City/Country: " + threeHourForecast.getCity().getName() + "/" + threeHourForecast.getCity().getCountry() + "\n"
                        + "Forecast Array Count: " + threeHourForecast.getCnt() + "\n"
                        //For this example, we are logging details of only the first forecast object in the forecasts array
                        + "First Forecast Date Timestamp: " + threeHourForecast.getList().get(5).getDt() + "\n"
                        + "First Forecast Weather Description: " + threeHourForecast.getList().get(5).getWeatherArray().get(0).getDescription() + "\n"
                        + "First Forecast Max Temperature: " + threeHourForecast.getList().get(5).getMain().getTempMax() + "\n"
                        + "First Forecast Wind Speed: " + threeHourForecast.getList().get(5).getWind().getSpeed() + "\n");
                forecast = threeHourForecast;
                setTextViews();

                Log.e("1", String.valueOf(threeHourForecast));

            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.v(TAG, throwable.getMessage());
            }
        });
    }
}
