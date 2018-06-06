package com.MeetUp.meetup.screens.auth.modules.intro;

import com.MeetUp.meetup.utils.LocationDataRetriever;
import com.MeetUp.meetup.utils.base.BaseView;

import io.reactivex.Observable;

public interface IntroFragmentView extends BaseView {
    Observable<Boolean> isPermissionGranted();
    Observable<Boolean> getPermissionResult();
    Observable<LocationDataRetriever> onLocationUpdates();
    Observable<Boolean> getActivityResult();
    Observable<Object> onSignUpClick();
    Observable<Object> onSignInClick();
    void showSignIn(String cityName, String countyName);
    void showSignUp();
    void requestPermission();
    void startLocationService();
    void showAlert(CharSequence data);
    void showError(int errorCode);
    void hideLoading();
    void showLoading();
    void checkLocationError(Throwable throwable);
}
