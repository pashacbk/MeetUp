package com.MeetUp.meetup.screens.auth.modules.signin;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.MeetUp.meetup.MeetUpApp;
import com.MeetUp.meetup.R;
import com.MeetUp.meetup.data.assets.User;
import com.MeetUp.meetup.screens.home.HomeActivity;
import com.MeetUp.meetup.utils.base.BaseFragment;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Observable;

import static android.view.View.GONE;

public class SignInFragment extends BaseFragment implements SignInView {

    public static final String TAG = SignInFragment.class.getSimpleName();
    public static final String CITY = "city";
    public static final String COUNTRY = "country";

    @BindView(R.id.signin_input_email)
    TextInputLayout mEmail;
    @BindView(R.id.signin_input_password)
    TextInputLayout mPassword;
    @BindView(R.id.edittext_email_signin)
    EditText emailInput;
    @BindView(R.id.edittext_password_signin)
    EditText passwordInput;
    @BindView(R.id.btn_login)
    Button button;
    @BindView(R.id.loader_container)
    ViewGroup loaderContainer;
    @BindView(R.id.sign_in_location)
    TextView locationField;
    private AlertDialog dialog;
    @Inject
    SignInPresenter presenter;


    public static BaseFragment newInstance(String cityName, String countryName) {
        SignInFragment fragment = new SignInFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CITY, cityName);
        bundle.putString(COUNTRY, countryName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.attachView(this);
        presenter.handleLocation();
    }

    @Override
    public Observable<Object> setSignInButtonClickListener() {
        return RxView.clicks(button);
    }

    @Override
    public Observable<CharSequence> setEmailTextListener() {
        return RxTextView.textChanges(emailInput);
    }

    @Override
    public Observable<CharSequence> setPasswordTextListener() {
        return RxTextView.textChanges(passwordInput);
    }

    @Override
    public void setupFragmentComponent() {
        MeetUpApp.getAppComponent()
                .signInBuilder()
                .build()
                .inject(this);
    }

    @Override
    public void clearEmailValidationError() {
        mEmail.setError(null);
    }

    @Override
    public void showEmailValidationError(int errorCode) {
        mEmail.setError(getString(errorCode));
    }

    @Override
    public void clearPasswordValidationError() {
        mPassword.setError(null);
    }

    @Override
    public void showPasswordValidationError(int errorCode) {
        mPassword.setError(getString(errorCode));
    }

    @Override
    public void manageButtonState(boolean enable) {
        button.setEnabled(enable);
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
                .setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss()).show();
    }

    @Override
    public void startHomeActivity(User user, String lat, String lon) {
        HomeActivity.newIntent(getActivity(), user, lat, lon);
    }

    @Override
    public void showError(int errorMessage) {
        showAlert(getString(errorMessage));
    }

    @Override
    public void hideLoading() {
        loaderContainer.setVisibility(GONE);
    }

    @Override
    public void showLoading() {
        loaderContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public String getCityLocation() {
        return getArguments().getString(CITY);
    }

    @Override
    public void showLocationField(String city) {
        locationField.setVisibility(View.VISIBLE);
        locationField.setText(city);
    }

    @Override
    public void onDestroyView() {
        presenter.detachView();
        super.onDestroyView();
    }
}
