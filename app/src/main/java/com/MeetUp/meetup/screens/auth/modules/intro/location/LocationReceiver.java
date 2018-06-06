package com.MeetUp.meetup.screens.auth.modules.intro.location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import com.google.android.gms.common.api.ResolvableApiException;

import io.reactivex.Observable;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subjects.PublishSubject;

public class LocationReceiver extends BroadcastReceiver {

    public static final String CODE_KEY = "code_key";
    public static final String ERROR = "error";
    public static final String LOCATION_SERVICE_RESPONSE_KEY = "location_service_response_key";
    private PublishSubject<Location> locationSubject;

    public void setPublishSubject(PublishSubject<Location> locationSubject) {
        this.locationSubject = locationSubject;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (locationSubject != null) {
            if (intent.getStringExtra(CODE_KEY).equals(ERROR)) {
                ResolvableApiException resolvableApiException = (ResolvableApiException) intent
                        .getSerializableExtra(LOCATION_SERVICE_RESPONSE_KEY);
                RxJavaPlugins.setErrorHandler(e -> e = resolvableApiException);
                locationSubject.onError(resolvableApiException);
            } else {
                Location location = intent.getParcelableExtra
                        (LOCATION_SERVICE_RESPONSE_KEY);
                locationSubject.onNext(location);
            }
        }
    }

    public Observable<Location> getLocationUpdates() {
        return locationSubject;
    }
}
