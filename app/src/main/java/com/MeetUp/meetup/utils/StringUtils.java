package com.MeetUp.meetup.utils;
import android.support.annotation.Nullable;

import com.MeetUp.meetup.screens.splash.SplashPresenter;

import java.util.logging.Handler;


public class StringUtils {

    public static boolean isEmpty(@Nullable CharSequence str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(@Nullable CharSequence str) {
        return !isEmpty(str);
    }
}
