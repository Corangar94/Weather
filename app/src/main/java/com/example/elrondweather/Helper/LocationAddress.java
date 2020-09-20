package com.example.elrondweather.Helper;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.example.elrondweather.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class LocationAddress {
    private static final String TAG = "LocationAddress";
    static String result = null;
    public static void getAddressFromLocation(final double latitude, final double longitude,
                                              final Context context) {
        TextView textView = ((Activity) context).findViewById(R.id.dayText);
        Thread thread = new Thread() {
            @Override
            protected void finalize() throws Throwable {
                super.finalize();
                CurrentLocation.setLocationName(result);
                textView.setText(result);
            }

            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                try {
                    List<Address> addressList = geocoder.getFromLocation(
                            latitude, longitude, 1);
                    if (addressList != null && addressList.size() > 0) {
                        Address address = addressList.get(0);
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                            sb.append(address.getAddressLine(i)).append("\n");
                        }
                        sb.append(address.getLocality()).append("\n");
                        result = sb.toString();
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Unable connect to Geocoder", e);
                } finally {
                    Message message = Message.obtain();
                    if (result != null) {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        //result = "Latitude: " + latitude + " Longitude: " + longitude +
                        //       "\n\nAddress:\n" + result;
                        bundle.putString("address", result);
                        CurrentLocation.setLocationName(result);
                        if(textView!=null)
                        textView.setText(result);

                        message.setData(bundle);

                    } else {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        result = "Latitude: " + latitude + " Longitude: " + longitude +
                                "\n Unable to get address for this lat-long.";
                        bundle.putString("address", result);
                        message.setData(bundle);
                   }
                }
            }
        };
        thread.start();
    }
}