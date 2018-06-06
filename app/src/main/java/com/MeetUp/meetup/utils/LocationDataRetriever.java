package com.MeetUp.meetup.utils;

import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocationDataRetriever {

    private static final String TAG = LocationDataRetriever.class.getSimpleName();
    private final double latitude;
    private final double longitude;
    private final Geocoder geocoder;

    public LocationDataRetriever(double latitude, double longitude, Geocoder geocoder) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.geocoder = geocoder;
    }

    public String getCityName() {
        List<Address> addresses = getAddress();
        String city = "";
        if (CollectionUtils.isNotEmpty(addresses)) {
            Address address = addresses.get(0);
            city = address.getLocality();
        }
        return city;
    }

    public String getCountryName() {
        List<Address> addresses = getAddress();
        String country = "";
        if (CollectionUtils.isNotEmpty(addresses)) {
            Address address = addresses.get(0);
            country = address.getCountryCode();
        }
        return country;
    }

    private List<Address> getAddress() {
        List<Address> addresses = new ArrayList<>();
        try {
           addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        return addresses;
    }
}
