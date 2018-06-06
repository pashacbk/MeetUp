package com.MeetUp.meetup.screens.auth.modules.intro.location;

import android.location.Location;

public interface RequestLocationListener {
    void onSuccess(Location location);
    void onError(Throwable throwable);
}
