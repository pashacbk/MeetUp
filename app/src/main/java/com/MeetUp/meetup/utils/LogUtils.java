package com.MeetUp.meetup.utils;

import android.util.Log;

public class LogUtils {

    public static void eLog(String tag, String message, Throwable throwable) {
        try {
            Log.e(tag, message, throwable);
        } catch (Exception e) {
            //do nothing
        }

    }

    public static void dLog(String tag, String message, Throwable throwable) {
        Log.e(tag, message, throwable);
    }

    public static void dLog(String tag, String message) {
        Log.e(tag, message);
    }

}
