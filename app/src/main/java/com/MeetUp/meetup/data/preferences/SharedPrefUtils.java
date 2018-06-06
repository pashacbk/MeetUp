package com.MeetUp.meetup.data.preferences;

import android.content.SharedPreferences;

public class SharedPrefUtils {

    private static final String USER_GROUPS_REQUEST_TIME_KEY = "user_groups_request_time";
    private final SharedPreferences preferences;

    public SharedPrefUtils(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public void setUserGrupsRequestTime(long time) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(USER_GROUPS_REQUEST_TIME_KEY, time).apply();
    }

    public long getUserGroupsRequestTime() {
        return preferences.getLong(USER_GROUPS_REQUEST_TIME_KEY, 0);
    }
}
