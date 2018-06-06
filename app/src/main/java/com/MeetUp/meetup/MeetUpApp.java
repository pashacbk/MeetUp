package com.MeetUp.meetup;

import android.support.multidex.MultiDexApplication;

import com.MeetUp.meetup.di.AppComponent;
import com.MeetUp.meetup.di.DaggerAppComponent;
import com.MeetUp.meetup.di.module.AppModule;

public class MeetUpApp extends MultiDexApplication {

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static AppComponent getAppComponent() {
        return component;
    }
}
