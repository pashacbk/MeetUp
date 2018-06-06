package com.MeetUp.meetup.screens.auth.modules.signin;

import com.MeetUp.meetup.data.assets.User;
import com.MeetUp.meetup.utils.base.BaseView;

import io.reactivex.Observable;


public interface SignInView extends BaseView {
    Observable<Object> setSignInButtonClickListener();
    Observable<CharSequence> setEmailTextListener();
    Observable<CharSequence> setPasswordTextListener();
    void clearEmailValidationError();
    void showEmailValidationError(int errorCode);
    void clearPasswordValidationError();
    void showPasswordValidationError(int errorCode);
    void manageButtonState(boolean enable);
    void showAlert(CharSequence message);
    void startHomeActivity(User user, String lat, String lon);
    void showError(int errorMessage);
    void hideLoading();
    void showLoading();
    String getCityLocation();
    void showLocationField(String city);

}
