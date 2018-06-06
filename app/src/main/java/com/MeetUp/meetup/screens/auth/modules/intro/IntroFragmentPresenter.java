package com.MeetUp.meetup.screens.auth.modules.intro;

import com.MeetUp.meetup.R;
import com.MeetUp.meetup.utils.base.BasePresenter;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public class IntroFragmentPresenter extends BasePresenter<IntroFragmentView> {

    private static final String EMPTY_STRING = "";
    private String cityName = EMPTY_STRING;
    private String countryName = EMPTY_STRING;
    private static final String TAG = IntroFragmentPresenter.class.getSimpleName();

    @Override
    protected void onViewAttached() {
        super.onViewAttached();
        addDisposable(onCheckPermission());
        addDisposable(onGetPermissionResult());
        addDisposable(onGetLocation());
        addDisposable(onActivityResult());
        addDisposable(onSignInClick());
        addDisposable(onSignUpClick());
    }

    private Disposable onCheckPermission() {
        return getView().isPermissionGranted()
                .subscribe(granted -> {
                    if (granted) {
                        startLocationService();
                    } else {
                        getView().requestPermission();
                    }
                });
    }

    private Disposable onGetPermissionResult() {
        return getView().getPermissionResult()
                .subscribe(granted -> {
                    if (granted) {
                        startLocationService();
                    } else {
                        getView().showError(R.string.location_needed_explanation);
                    }
                });
    }

    private Disposable onSignInClick() {
        return getView().onSignInClick()
                .subscribe(o -> getView().showSignIn(cityName, countryName));
    }

    private Disposable onSignUpClick() {
        return getView().onSignUpClick()
                .subscribe(o -> getView().showSignUp());
    }

    private Disposable onActivityResult() {
        return getView().getActivityResult()
                .filter(isOk -> isOk)
                .subscribe(isOk -> startLocationService());
    }

    private Disposable onGetLocation() {
        return getView().onLocationUpdates()
                .subscribe(retriever -> {
                    countryName = retriever.getCountryName();
                    cityName = retriever.getCityName();
                    getView().hideLoading();
                }, throwable -> {
                    getView().checkLocationError(throwable);
                    getView().hideLoading();
                });
    }

    private void startLocationService() {
        getView().startLocationService();
        getView().showLoading();
    }
}
