package com.example.elrondweather.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;

import static com.example.elrondweather.R.layout.weather_item;

public class WeatherItemView extends CardView {
    private ArrayList<Integer> colorsArray;
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

    }




}
