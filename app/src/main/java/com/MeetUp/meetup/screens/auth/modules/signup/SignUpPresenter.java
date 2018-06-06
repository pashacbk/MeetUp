package com.MeetUp.meetup.screens.auth.modules.signup;

import com.MeetUp.meetup.R;
import com.MeetUp.meetup.utils.base.BasePresenter;
import com.MeetUp.meetup.utils.validate.Validator;

import io.reactivex.disposables.Disposable;


public class SignUpPresenter extends BasePresenter<SignUpView> {

    private final SignUpModel model;
    private final Validator<CharSequence> userNameValidator;
    private final Validator<CharSequence> emailValidator;
    private final Validator<CharSequence> passwordValidator;
    private final Validator<CharSequence> isEmptyValidator;
    private boolean isEmailValid;
    private boolean isPasswordValid;
    private boolean isUserNameValid;

    SignUpPresenter(SignUpModel model,
                           Validator<CharSequence> userNameValidator,
                           Validator<CharSequence> emailValidator,
                           Validator<CharSequence> passwordValidator,
                           Validator<CharSequence> isEmptyValidator) {
        this.model = model;
        this.userNameValidator = userNameValidator;
        this.emailValidator = emailValidator;
        this.passwordValidator = passwordValidator;
        this.isEmptyValidator = isEmptyValidator;
    }

    @Override
    protected void onViewAttached() {
        super.onViewAttached();
        addDisposable(onCountryClick());
        addDisposable(onSignUpButtonClick());
        addDisposable(onUsernameTextChanged());
        addDisposable(onPasswordTextChanged());
        addDisposable(onEmailTextChanged());
    }

    Disposable onEmailTextChanged() {
        return getView().setEmailTextListener()
                .subscribe(this::onEmailFieldChanged);
    }

    Disposable onUsernameTextChanged() {
        return getView().setUsernameTextListener()
                .subscribe(this::onUserNameFieldChanged);
    }

    Disposable onPasswordTextChanged() {
        return getView().setPasswordTextListener()
                .subscribe(this::onPasswordFieldChanged);
    }

    Disposable onCountryClick() {
        return getView().setOnCountryClickListener()
                .subscribe(o -> onChooseCountryClick());
    }

    Disposable onSignUpButtonClick() {
        return getView().setOnSignUpButtonClickListener()
                .subscribe(o -> {
                    if (isEmptyField(getView().provideChosenCountry()))
                        getView().showMessage(R.string.error_empty_country_field);
                });
    }

    private void onEmailFieldChanged(CharSequence data) {
        isEmailValid = false;
        setButtonEnable();
        if (isEmptyField(data)) {
            getView().clearEmailError();
            return;
        }
        if (emailValidator.isValid(data)) {
            getView().clearEmailError();
            isEmailValid = true;
            setButtonEnable();
            return;
        }
        getView().setEmailError(R.string.error_invalid_email);
    }

    private void onUserNameFieldChanged(CharSequence data) {
        isUserNameValid = false;
        setButtonEnable();
        if (isEmptyField(data)) {
            getView().clearUserNameError();
            return;
        }
        if (userNameValidator.isValid(data)) {
            getView().clearUserNameError();
            isUserNameValid = true;
            setButtonEnable();
            return;
        }
        getView().setUserNameError(R.string.error_invalid_user_name);
    }

    private void onPasswordFieldChanged(CharSequence data) {
        isPasswordValid = false;
        setButtonEnable();
        if (isEmptyField(data)) {
            getView().clearPasswordError();
            return;
        }
        if (passwordValidator.isValid(data)) {
            getView().clearPasswordError();
            isPasswordValid = true;
            setButtonEnable();
            return;
        }
        getView().setPasswordError(R.string.error_invalid_password);
    }

    void onChooseCountryClick() {
        getView().showCountriesDialog(model.getCountryList());
    }

    void onChooseCountryFromListClick(String s) {
        getView().setChosenCountry(s);
    }

    private boolean isEmptyField(CharSequence data) {
        return isEmptyValidator.isValid(data);
    }

    private void setButtonEnable() {
        getView().setButtonEnable(isEmailValid && isPasswordValid && isUserNameValid);
    }
}
