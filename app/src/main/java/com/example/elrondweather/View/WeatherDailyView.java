package com.example.elrondweather.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import static com.example.elrondweather.R.layout.daily_weather_item;

public class WeatherDailyView extends CardView{


    public WeatherDailyView(Context context) {
        super(context);
        init(context);

    }

    public WeatherDailyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public WeatherDailyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    public void init(Context context) {
        LayoutInflater.from(context).inflate(daily_weather_item, this, true);

    }
}
