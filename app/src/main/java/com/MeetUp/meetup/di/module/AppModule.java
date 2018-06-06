package com.MeetUp.meetup.di.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;

import com.MeetUp.meetup.data.preferences.SharedPrefUtils;
import com.MeetUp.meetup.di.ApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private static final String MEET_UP_PREFERENCES = "MeetUpPreferences";
    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @ApplicationScope
    public Context providesContext() {
        return context;
    }

    @Provides
    @ApplicationScope
    AssetManager provideAssetsManager(Context context) {
        return context.getAssets();
    }

    @Provides
    @ApplicationScope
    public SharedPrefUtils providesSharedPreferencies(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MEET_UP_PREFERENCES, Context.MODE_PRIVATE);
        return new SharedPrefUtils(sharedPreferences);
    }
}
