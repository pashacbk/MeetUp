package com.MeetUp.meetup.utils;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.MeetUp.meetup.utils.exceptions.NoNetworkException;

import io.reactivex.Observable;


public class NetworkUtils {

    private static final String NO_NETWORK_MESSAGE = "No Internet Connection";
    private final ConnectivityManager connectivityManager;

    public NetworkUtils(ConnectivityManager connectivityManager) {
        this.connectivityManager = connectivityManager;
    }

    public boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public boolean isNoNetworkConnection() {
        return !isNetworkAvailable();
    }

    public Observable<Boolean> isNetworkAvailableRx() throws NoNetworkException {
        return Observable.just(isNetworkAvailable())
                .filter(isNetworkAvailable -> {
                    if (!isNetworkAvailable) {
                        throw new NoNetworkException(NO_NETWORK_MESSAGE);
                    }
                    return true;
                });
    }
}
