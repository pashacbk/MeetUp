package com.MeetUp.meetup.screens.auth.modules.signup;


import com.MeetUp.meetup.utils.base.BaseView;

import java.util.List;

import io.reactivex.Observable;


public interface SignUpView extends BaseView {

    void setEmailError(int errorMessage);
    void setPasswordError(int errorMessage);
    void setUserNameError(int errorMessage);
    void setButtonEnable(boolean enable);
    void showCountriesDialog(List<String> countryList);
    void setChosenCountry(String countryName);
    void showMessage(int errorMessage);
    void clearEmailError();
    void clearUserNameError();
    void clearPasswordError();
    Observable<Object> setOnSignUpButtonClickListener();
    Observable<Object> setOnCountryClickListener();
    CharSequence provideChosenCountry();
    Observable<CharSequence> setUsernameTextListener();
    Observable<CharSequence> setPasswordTextListener();
    Observable<CharSequence> setEmailTextListener();
}
