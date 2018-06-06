package com.MeetUp.meetup.di.module;

import android.content.Context;
import android.net.ConnectivityManager;

import com.MeetUp.meetup.di.ApplicationScope;
import com.MeetUp.meetup.utils.NetworkUtils;

import dagger.Module;
import dagger.Provides;

@Module
public class UtilsModule {

    @Provides
    @ApplicationScope
    NetworkUtils providesNetworkUtils(ConnectivityManager manager) {
        return new NetworkUtils(manager);
    }

    @Provides
    @ApplicationScope
    ConnectivityManager providesConnectivityManager(Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }
}
