package com.MeetUp.meetup.screens.auth.modules.intro.location;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocationHelper {

    private static final String TAG = LocationHelper.class.getSimpleName();
    public static final int TIME_BETWEEN_REQUESTS = 10000;
    private LocationCallback locationCallback;
    private final FusedLocationProviderClient fusedLocationClient;
    private final RequestLocationListener callback;
    private LocationRequest locationRequest;
    private LocationSettingsRequest locationSettings;
    private final SettingsClient settingsClient;


    public LocationHelper(FusedLocationProviderClient fusedLocationClient, RequestLocationListener callback, SettingsClient settingsClient) {
        this.fusedLocationClient = fusedLocationClient;
        this.callback = callback;
        this.settingsClient = settingsClient;
    }

    @SuppressLint("MissingPermission")
    public void getLastUserLocation() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location == null) {
                            prepareAndStartLocationUpdates();
                        } else {
                            callback.onSuccess(location);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, e.getLocalizedMessage(), e);
                        prepareAndStartLocationUpdates();
                    }
                });
    }

    @SuppressLint("MissingPermission")
    private void prepareAndStartLocationUpdates() {
        setLocationRequest();
        setLocationSettingsRequest();
        setLocationCallBack();
        startLocationUpdates();
    }

    private void setLocationRequest() {
         locationRequest = LocationRequest.create()
                .setInterval(TIME_BETWEEN_REQUESTS)
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    private void setLocationSettingsRequest() {
        locationSettings = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .build();
    }

    private void setLocationCallBack() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                callback.onSuccess(locationResult.getLastLocation());
            }
        };
    }

    private void startLocationUpdates() {
        settingsClient.checkLocationSettings(locationSettings)
                .addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        fusedLocationClient.requestLocationUpdates(locationRequest,
                                locationCallback, Looper.myLooper());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onError(e);
                    }
                });
    }
}
