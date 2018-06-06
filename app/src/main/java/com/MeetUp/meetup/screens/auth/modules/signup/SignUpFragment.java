package com.MeetUp.meetup.screens.auth.modules.signup;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.MeetUp.meetup.R;
import com.MeetUp.meetup.utils.base.BaseFragment;
import com.MeetUp.meetup.utils.validate.RegexValidator;
import com.MeetUp.meetup.utils.view.ListAlertDialog;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import io.reactivex.Observable;


public class SignUpFragment extends BaseFragment implements SignUpView {

    public static final String TAG = SignUpFragment.class.getSimpleName();
    private static final Pattern USERNAME_PATTERN = Pattern.compile("([0-9a-zA-Z]+){0,}");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(".{8,}");
    private static final Pattern EMPTY_PATTERN = Pattern.compile("^$");
    public static final String CHOOSE_COUNTRY_KEY = "choose_country_key";
    private static final String IS_DIALOG_SHOWING_KEY = "is_dialog_showing";

    @BindView(R.id.input_name)
    TextInputLayout userName;
    @BindView(R.id.input_email)
    TextInputLayout email;
    @BindView(R.id.input_password)
    TextInputLayout password;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_sign_up)
    Button button;
    @BindView(R.id.choose_country)
    TextView chooseCountry;
    private SignUpPresenter presenter;
    private ListAlertDialog dialog;

    public static BaseFragment newInstance() {
        return new SignUpFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        SignUpModel model = new SignUpModel();
        presenter = new SignUpPresenter(model,
                new RegexValidator(USERNAME_PATTERN),
                new RegexValidator(Patterns.EMAIL_ADDRESS),
                new RegexValidator(PASSWORD_PATTERN),
                new RegexValidator(EMPTY_PATTERN));
        presenter.attachView(this);
    }

    @Override
    public void setupFragmentComponent() { }

    @Override
    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
        if (bundle != null) {
            chooseCountry.setText(bundle.getCharSequence(CHOOSE_COUNTRY_KEY));
            if (bundle.getBoolean(IS_DIALOG_SHOWING_KEY)) {
                presenter.onChooseCountryClick();
            }
        }
    }

    @Override
    public void setUserNameError(int errorMessage) {
        userName.setError(getString(errorMessage));
    }

    @Override
    public void clearEmailError() {
        email.setError(null);
    }

    @Override
    public void clearUserNameError() {
        userName.setError(null);
    }

    @Override
    public void clearPasswordError() {
        password.setError(null);
    }

    @Override
    public void setEmailError(int errorMessage) {
        email.setError(getString(errorMessage));
    }

    @Override
    public void setPasswordError(int errorMessage) {
        password.setError(getString(errorMessage));
    }

    @Override
    public void setButtonEnable(boolean enable) {
        button.setEnabled(enable);
    }

    @Override
    public void showCountriesDialog(final List<String> data) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, data);
        dialog = new ListAlertDialog(getActivity(), adapter);
        dialog.setTitle(getString(R.string.choose_country));
        dialog.show();
        dialog.setListener((parent, view, position, id) -> {
            presenter.onChooseCountryFromListClick(data.get(position));
            dialog.dismiss();
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence(CHOOSE_COUNTRY_KEY, chooseCountry.getText());
        if (dialog != null) {
            outState.putBoolean(IS_DIALOG_SHOWING_KEY, dialog.isShowing());
        }
    }

    @Override
    public void setChosenCountry(String countryName) {
        chooseCountry.setText(countryName);
    }

    @Override
    public void showMessage(int errorMessage) {
        Snackbar.make(getView(), errorMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public Observable<CharSequence> setEmailTextListener() {
        return RxTextView.textChanges(etEmail);
    }
    @Override
    public Observable<CharSequence> setPasswordTextListener() {
        return RxTextView.textChanges(etPassword);
    }
    @Override
    public Observable<CharSequence> setUsernameTextListener() {
        return RxTextView.textChanges(etName);
    }

    @Override
    public Observable<Object> setOnSignUpButtonClickListener() {
        return RxView.clicks(button);
    }
    @Override
    public Observable<Object> setOnCountryClickListener() {
        return RxView.clicks(chooseCountry);
    }
    @Override
    public CharSequence provideChosenCountry() {
        return chooseCountry.getText();
    }

    @Override
    public void onDestroyView() {
        presenter.detachView();
        super.onDestroyView();
    }
}