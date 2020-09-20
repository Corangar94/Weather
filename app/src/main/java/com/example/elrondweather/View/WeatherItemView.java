package com.example.elrondweather.View;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.elrondweather.Helper.CurrentLocation;
import com.example.elrondweather.Helper.WeatherClasses.JsonToJava.Weather;
import com.example.elrondweather.R;

import java.util.ArrayList;

import static com.example.elrondweather.R.layout.weather_item;

public class WeatherItemView extends CardView implements View.OnClickListener{
    private ArrayList<Integer> colorsArray;
    TextView location,rainChance, temperature, humidity;
    public WeatherItemView(Context context) {
        super(context);
        init(context);

    }

    public WeatherItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public WeatherItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    public void init(Context context) {
        LayoutInflater.from(context).inflate(weather_item, this, true);
        rainChance = findViewById(R.id.rainChance);
        temperature = findViewById(R.id.temperature);
        humidity = findViewById(R.id.humidity);
        location = findViewById(R.id.dayText);
    }

    public void setWeatherItem(Weather weather){
        location.setText(CurrentLocation.getLocationName());
        rainChance.setText(String.valueOf(Math.round(weather.getDaily().get(0).getPop())));
        temperature.setText(String.valueOf(Math.round(weather.getDaily().get(0).getTemp().getDay())));
        humidity.setText(String.valueOf(Math.round(weather.getDaily().get(0).getHumidity())));
        View view = findViewById(R.id.view);
        view.setOnClickListener(onClick);
    }
    @Override
    public void onClick(View v) {
        gotoNextPage(v);
    }
    final OnClickListener onClick = v -> gotoNextPage(findViewById(R.id.view));
    public void gotoNextPage(View v) {
        Intent intent = new Intent(getContext(), ExtendedWeatherView.class);
        intent.putExtra("SELECTED_DAY", 0);
        v.getContext().startActivity(intent);
    }
}
