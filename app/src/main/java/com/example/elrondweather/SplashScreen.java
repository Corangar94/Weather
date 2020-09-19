package com.example.elrondweather;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.elrondweather.Helper.AppLocationService;
import com.example.elrondweather.Helper.CurrentLocation;
import com.example.elrondweather.Helper.FallbackLocationTracker;
import com.example.elrondweather.Helper.LocationAddress;
import com.example.elrondweather.Helper.Retrofit.WeatherCallback;
import com.example.elrondweather.Helper.WeatherAPICall;
import com.example.elrondweather.Helper.WeatherClasses.JsonToJava.Weather;
import com.example.elrondweather.Helper.WeatherClasses.WeatherForecast;

import java.util.Objects;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class SplashScreen extends AppCompatActivity {
    protected static final String TAG = "LocationOnOff";
    final static int REQUEST_LOCATION = 199;
    static boolean isGpsEnabled;
    private static SplashScreen parent;
    private final int REQUEST_LOCATION_PERMISSION = 1;
    String result;
    AppLocationService appLocationService;
    String[] perms;
    boolean askedGPS;
    private Location location;
    String locationData;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        context = getApplicationContext();
        IsGPSEnabled();
        perms = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
        requestLocationPermission();
    }

    private boolean IsGPSEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isGpsEnabled;
    }

    private void delayIntent(int time) {
        new Handler().postDelayed(() -> {
            Intent i = new Intent(SplashScreen.this, MainActivity.class);
            i.putExtra("location", location);
            startActivity(i);
            finish();
        }, time);
    }

    private void CheckLocation(int time) {

        new Handler().postDelayed(() -> {
            IsGPSEnabled();
            if (EasyPermissions.hasPermissions(this, perms) && isGpsEnabled)
                if (CurrentLocation.getLocation() != null) {
                    WeatherAPICall weatherAPICall = new WeatherAPICall();
                    LocationAddress.getAddressFromLocation(CurrentLocation.getLocation().getLatitude(),CurrentLocation.getLocation().getLongitude(),this);
                    weatherAPICall.loadInBackground(new WeatherCallback() {
                        @Override
                        public void onSuccess(Weather weather) {
                            WeatherForecast.setWeather(weather);
                            delayIntent(0);
                        }

                        @Override
                        public void onFailure(Throwable throwable) {
                            throwable.getMessage();
                            Log.e("NOEBIEN", Objects.requireNonNull(throwable.getMessage()));
                        }
                    });

                } else {
                    setLocation();
                }
            else if (!isGpsEnabled && !askedGPS) {
                SettingsRequest();
                askedGPS = true;
                CheckLocation(1000);
            } else
                CheckLocation(1000);
        }, 1000);
    }

    private void setLocation() {
        FallbackLocationTracker tracker = new FallbackLocationTracker(this);
        tracker.start((oldLoc, oldTime, newLoc, newTime) -> {
            CurrentLocation.setLocation(newLoc);
            CheckLocation(10000);
        });
        tracker.start();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        Log.e("Asd", String.valueOf(EasyPermissions.hasPermissions(this, perms)));
        if (EasyPermissions.hasPermissions(this, perms)) {
            FallbackLocationTracker tracker = new FallbackLocationTracker(this);
            tracker.start((oldLoc, oldTime, newLoc, newTime) -> {
                CurrentLocation.setLocation(newLoc);
            });
        }
    }

    @AfterPermissionGranted(REQUEST_LOCATION_PERMISSION)
    public void requestLocationPermission() {
        Log.e("Permisiune","2");
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, "Please grant the location permission", REQUEST_LOCATION_PERMISSION, perms);
        }
        CheckLocation(0);
    }

    public void SettingsRequest() {
        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }
    }
}





