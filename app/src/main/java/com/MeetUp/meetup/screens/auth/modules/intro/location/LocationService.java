package com.MeetUp.meetup.screens.auth.modules.intro.location;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.MeetUp.meetup.MeetUpApp;
import com.MeetUp.meetup.screens.auth.modules.intro.location.dagger.LocationServiceModule;

import javax.inject.Inject;

public class LocationService extends IntentService implements RequestLocationListener {

    public static final String CODE_KEY = "code_key";
    public static final String LOCATION = "location";
    public static final String LOCATION_SERVICE_RESPONSE_KEY = "location_service_response_key";
    public static final String ERROR = "error";
    public static final String LOCATION_RECEIVER_ACTION = "com.meetup.PROVIDE_LOCATION";

    @Inject
    LocationHelper locationHelper;

    public LocationService() {
        super("LocationService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MeetUpApp
                .getAppComponent()
                .locationServiceBuilder()
                .locationServiceComponent(new LocationServiceModule(this))
                .build().inject(this);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        locationHelper.getLastUserLocation();
    }

    @Override
    public void onSuccess(Location location) {
        sendResult(new Intent(LOCATION_RECEIVER_ACTION)
                .putExtra(CODE_KEY, LOCATION)
                .putExtra(LOCATION_SERVICE_RESPONSE_KEY, location));
    }

    @Override
    public void onError(Throwable throwable) {
        sendResult(new Intent(LOCATION_RECEIVER_ACTION)
                .putExtra(CODE_KEY, ERROR)
                .putExtra(LOCATION_SERVICE_RESPONSE_KEY, throwable));
    }

    private void sendResult(Intent intent) {
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
