package com.example.elrondweather.Helper;

import android.location.Location;

public class CurrentLocation {
    static Location location;
    static String locationName;
    public static Location getLocation() {
        return location;
    }

    public static void setLocation(Location setLocation) {
        location = setLocation;
    }

    public static String getLocationName() {
        return locationName;
    }

    public static void setLocationName(String locationName) {
        CurrentLocation.locationName = locationName;
    }

}
