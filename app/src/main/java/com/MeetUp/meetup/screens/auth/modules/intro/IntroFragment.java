package com.MeetUp.meetup.screens.auth.modules.intro;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.MeetUp.meetup.MeetUpApp;
import com.MeetUp.meetup.R;
import com.MeetUp.meetup.screens.auth.modules.intro.dagger.IntroFragmentModule;
import com.MeetUp.meetup.screens.auth.modules.intro.location.LocationReceiver;
import com.MeetUp.meetup.screens.auth.modules.intro.location.LocationService;
import com.MeetUp.meetup.screens.auth.modules.signin.SignInFragment;
import com.MeetUp.meetup.screens.auth.modules.signup.SignUpFragment;
import com.MeetUp.meetup.utils.LocationDataRetriever;
import com.MeetUp.meetup.utils.base.BaseFragment;
import com.google.android.gms.common.api.ResolvableApiException;
import com.jakewharton.rxbinding2.view.RxView;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

import static com.MeetUp.meetup.screens.auth.modules.intro.location.LocationService.LOCATION_RECEIVER_ACTION;

public class IntroFragment extends BaseFragment implements IntroFragmentView {

    public static final String TAG = IntroFragment.class.getSimpleName();
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 9991;
    public static final int REQUEST_CHECK_SETTINGS = 9992;
    public static String LOCATION_PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private AlertDialog dialog;
    private LocationReceiver locationReceiver;
    private LocalBroadcastManager broadcastManager;
    private IntentFilter intentFilter;
    private PublishSubject<Boolean> permissionObservable = PublishSubject.create();
    private PublishSubject<Boolean> activityResultObservable = PublishSubject.create();
    private PublishSubject<Location> locationSubject = PublishSubject.create();

    @BindView(R.id.sign_in)
    Button signInButton;
    @BindView(R.id.sign_up)
    Button signUpButton;
    @BindView(R.id.loader_container)
    ViewGroup loaderContainer;

    @Inject
    IntroFragmentPresenter presenter;
    @Inject
    Geocoder geocoder;

    public static BaseFragment newInstance() {
        return new IntroFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_intro, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        locationReceiver = new LocationReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction(LOCATION_RECEIVER_ACTION);
        locationReceiver.setPublishSubject(locationSubject);
        broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        presenter.attachView(this);
    }

    @Override
    public void setupFragmentComponent() {
        MeetUpApp.getAppComponent()
                .introFragmentBuilder()
                .introFragmentModule(new IntroFragmentModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        broadcastManager.registerReceiver(
                locationReceiver,
                intentFilter);
    }

    @Override
    public void showSignIn(String cityName, String countryName) {
        replaceFragment(R.id.container, SignInFragment.newInstance(cityName, countryName), SignInFragment
                .TAG, true);
    }

    @Override
    public void showSignUp() {
        replaceFragment(R.id.container, SignUpFragment.newInstance(), SignUpFragment.TAG, true);
    }

    @Override
    public Observable<Boolean> isPermissionGranted() {
        return Observable.just(ActivityCompat.checkSelfPermission(getActivity(),
                LOCATION_PERMISSION) == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public Observable<Object> onSignInClick() {
        return RxView.clicks(signInButton);
    }

    @Override
    public Observable<Object> onSignUpClick() {
        return RxView.clicks(signUpButton);
    }

    @Override
    public void requestPermission() {
        requestPermissions(new String[]{LOCATION_PERMISSION},
                LOCATION_PERMISSION_REQUEST_CODE);
    }

    @Override
    public Observable<Boolean> getPermissionResult() {
        return permissionObservable;
    }

    @Override
    public Observable<Boolean> getActivityResult() {
        return activityResultObservable;
    }

    @Override
    public Observable<LocationDataRetriever> onLocationUpdates() {
        return locationReceiver.getLocationUpdates()
                .map(location -> new LocationDataRetriever(location.getLatitude(),
                        location.getLongitude(),
                        geocoder));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionObservable.onNext(requestCode == LOCATION_PERMISSION_REQUEST_CODE
                && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        activityResultObservable.onNext(requestCode == REQUEST_CHECK_SETTINGS
                && resultCode == Activity.RESULT_OK);
    }

    @Override
    public void startLocationService() {
        getActivity().startService(new Intent(getActivity(), LocationService.class));
    }

    @Override
    public void showAlert(CharSequence message) {
        if (dialog != null && dialog.isShowing()) {
            dialog.setMessage(message);
            return;
        }
        dialog = new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    public void showError(int errorCode) {
        showAlert(getString(errorCode));
    }

    @Override
    public void hideLoading() {
        loaderContainer.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        loaderContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void checkLocationError(Throwable throwable) {
        ResolvableApiException resolvableApiException = (ResolvableApiException) throwable;
        try {
            startIntentSenderForResult(resolvableApiException.getResolution().getIntentSender(),
                    REQUEST_CHECK_SETTINGS, null, 0,0,0, null );
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        broadcastManager.unregisterReceiver(locationReceiver);
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        presenter.detachView();
        super.onDestroyView();
    }
}
