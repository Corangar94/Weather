package com.example.elrondweather;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.elrondweather.Helper.CurrentLocation;
import com.example.elrondweather.Helper.FallbackLocationTracker;
import com.example.elrondweather.Helper.LocationAddress;
import com.example.elrondweather.Helper.ReadFromFile;
import com.example.elrondweather.Helper.Retrofit.WeatherCallback;
import com.example.elrondweather.Helper.WeatherAPICall;
import com.example.elrondweather.Helper.WeatherClasses.JsonToJava.Weather;
import com.example.elrondweather.Helper.WeatherClasses.WeatherForecast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class SplashScreen extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    static boolean isGpsEnabled;
    private final int REQUEST_LOCATION_PERMISSION = 1;
    String[] perms;
    private Location location;
    Context context;
    final static int REQUEST_LOCATION = 199;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    PendingResult<LocationSettingsResult> result;
    private FallbackLocationTracker tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        context = getApplicationContext();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        tracker = new FallbackLocationTracker(SplashScreen.this);
        perms = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
        checkAllPermissions();


    }

    private void checkAllPermissions() {
        if (isGpsEnabled && hasRequiredPermissions())
            CheckLocation();
        else if (!isGpsEnabled)
            mGoogleApiClient.connect();
        else
            requestLocationPermission();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isGpsEnabled && (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                || (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED))
            setLocation();
    }

    private void delayIntent(int time) {
        new Handler().postDelayed(() -> {
            Intent i = new Intent(SplashScreen.this, MainActivity.class);
            i.putExtra("location", location);
            startActivity(i);
            finish();
        }, time);
    }

    private void CheckLocation() {
        if (hasRequiredPermissions()) {
            Log.e("AICI", "PLM");
            setLocation();
            if (CurrentLocation.getLocation() != null) {
                WeatherAPICall weatherAPICall = new WeatherAPICall();
                LocationAddress.getAddressFromLocation(CurrentLocation.getLocation().getLatitude(), CurrentLocation.getLocation().getLongitude(), this);
                weatherAPICall.loadInBackground(new WeatherCallback() {
                    @Override
                    public void onSuccess(Weather weather) {
                        WeatherForecast.setWeather(weather);
                        ReadFromFile.saveObject(weather);
                        delayIntent(0);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        throwable.getMessage();
                    }
                });
            }
        } else checkAllPermissions();
    }

    private boolean hasRequiredPermissions() {
        return (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                || (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }

    private void setLocation() {
        tracker.start((oldLocation, oldTime, newLocation, newTime) -> {
            location = newLocation;
            CurrentLocation.setLocation(location);
            CheckLocation();
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            requestLocationPermission();
        }
    }

    @AfterPermissionGranted(REQUEST_LOCATION_PERMISSION)
    public void requestLocationPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, "Please grant the location permission", REQUEST_LOCATION_PERMISSION, perms);
        }
        CheckLocation();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(30 * 1000);
        mLocationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true);
        result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(result -> {
            final Status status = result.getStatus();
            switch (status.getStatusCode()) {
                case LocationSettingsStatusCodes.SUCCESS:
                    requestLocationPermission();
                    break;
                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                    try {
                        // check the result in onActivityResult().
                        status.startResolutionForResult(
                                SplashScreen.this,
                                REQUEST_LOCATION);
                    } catch (IntentSender.SendIntentException e) {
                    }
                    break;
                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                    Weather weather = ReadFromFile.getObject();
                    if (weather != null) {
                        WeatherForecast.setWeather(weather);
                        delayIntent(0);
                    }
                    break;
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOCATION) {
            switch (resultCode) {
                case Activity.RESULT_OK: {
                    // All required changes were successfully made
                    Toast.makeText(SplashScreen.this, "Location enabled by user!", Toast.LENGTH_LONG).show();
                    requestLocationPermission();
                    break;
                }
                case Activity.RESULT_CANCELED: {
                    // The user was asked to change settings, but chose not to
                    Toast.makeText(SplashScreen.this, "Location not enabled, checking cache", Toast.LENGTH_LONG).show();
                    Weather weather = ReadFromFile.getObject();
                    Log.e("Weather", String.valueOf(weather));
                    if (weather != null) {
                        WeatherForecast.setWeather(weather);
                        delayIntent(0);
                    }
                    break;
                }
                default: {
                    break;
                }
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Weather weather = ReadFromFile.getObject();
        if (weather != null) {
            WeatherForecast.setWeather(weather);
            delayIntent(0);
        }
    }


}





