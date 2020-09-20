package com.example.elrondweather.View;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.elrondweather.Helper.WeatherClasses.JsonToJava.Weather;
import com.example.elrondweather.R;

import java.util.Calendar;

import static com.example.elrondweather.R.layout.daily_weather_item;

public class WeatherDailyView extends CardView implements View.OnClickListener{
    TextView dayText, temperature;
    ImageView icon;
    int index;

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
        dayText = findViewById(R.id.dayText);
        temperature = findViewById(R.id.temperature);
        icon = findViewById(R.id.icon);
        View view = findViewById(R.id.view);
        view.setOnClickListener(onClick);
    }

    final OnClickListener onClick = v -> gotoNextPage(findViewById(R.id.view));

    public void setDailyItem(Weather weather, int index){
        this.index = index;
        dayText.setText(getCurrentDay(index));
        temperature.setText(String.valueOf(Math.round(weather.getDaily().get(index).getTemp().getDay())));

    }
    public String getCurrentDay(int index){

        String[] daysArray = {"Monday","Tuesday", "Wednesday","Thursday","Friday", "Saturday", "Sunday",};

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        day+=index-2;
        return daysArray[day];
    }

    @Override
    public void onClick(View v) {
        gotoNextPage(v);
    }

    public void gotoNextPage(View v) {
        Intent intent = new Intent(getContext(), ExtendedWeatherView.class);
        intent.putExtra("SELECTED_DAY", index);
        v.getContext().startActivity(intent);
    }

}
