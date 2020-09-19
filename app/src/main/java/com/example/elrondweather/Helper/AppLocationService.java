package com.example.elrondweather.Helper;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.provider.Settings;

import com.example.elrondweather.R;


public class AppLocationService extends Service {

    protected LocationManager locationManager;
    Location gpsLocation;

    private static final long MIN_DISTANCE_FOR_UPDATE = 10;
    private static final long MIN_TIME_FOR_UPDATE = 1000 * 60 * 2;

    public AppLocationService(Context context) {
        locationManager = (LocationManager) context
                .getSystemService(LOCATION_SERVICE);
    }

    public Location getLocation() {
        return gpsLocation;
    }
    public void showSettingsAlert(Context context) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle("Location needed!");

        // Setting Dialog Message
        alertDialog.setMessage("Please enable your location!");

        // On pressing Settings button
        alertDialog.setPositiveButton(context.getApplicationContext().getResources().getString(R.string.bottom_sheet_behavior),
                (dialog, which) -> {
                    Intent intent = new Intent(
                            Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                });

        alertDialog.show();
    }
    public boolean canGetLocation(Context context) {
        boolean result = true;
        LocationManager lm;
        boolean gpsEnabled = false;
        boolean networkEnabled = false;

        lm = (LocationManager) context
                .getSystemService(LOCATION_SERVICE);

        // exceptions will be thrown if provider is not permitted.
        try {
            assert lm != null;
            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            networkEnabled = lm
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        return gpsEnabled && networkEnabled;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }


}