package com.MeetUp.meetup.screens.auth.modules.signin;

import com.MeetUp.meetup.R;
import com.MeetUp.meetup.utils.StringUtils;
import com.MeetUp.meetup.utils.base.BasePresenter;
import com.MeetUp.meetup.utils.validate.Validator;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.MeetUp.meetup.screens.auth.modules.signin.dagger.SignInModule.EMAIL_VALIDATOR;
import static com.MeetUp.meetup.screens.auth.modules.signin.dagger.SignInModule.EMPTY_VALIDATOR;
import static com.MeetUp.meetup.screens.auth.modules.signin.dagger.SignInModule.PASSWORD_VALIDATOR;


public class SignInPresenter extends BasePresenter<SignInView> {

    private boolean isEmailValid;
    private boolean isPasswordValid;
    private final SignInModel model;
    private final Map<String, Validator<CharSequence>> validators;
    private String email;
    private String password;

    public SignInPresenter(Map<String, Validator<CharSequence>> validators,
                           SignInModel model) {
        this.validators = validators;
        this.model = model;
    }

    @Override
    protected void onViewAttached() {
        super.onViewAttached();
        addDisposable(onEmailTextChanged());
        addDisposable(onSignInButtonClick());
        addDisposable(onPasswordTextChanged());
    }

    private Disposable onEmailTextChanged() {
        return getView().setEmailTextListener()
                .subscribe(this::onEmailTextChanged);
    }

    private Disposable onPasswordTextChanged() {
        return getView().setPasswordTextListener()
                .subscribe(this::onPasswordTextChanged);
    }

    private Disposable onSignInButtonClick() {
        return getView().setSignInButtonClickListener()
                .doOnNext(o -> {
                    getView().showLoading();
                    getView().manageButtonState(false);})
                .observeOn(Schedulers.io())
                .flatMap(o -> model.doSignIn(email, password))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    if (user.getApiKey() == null) {
                        getView().hideLoading();
                        getView().showError(R.string.error_incorrect_data);
                        getView().manageButtonState(true);
                    } else {
                        getView().hideLoading();
                        getView().startHomeActivity(user, "47.03", "28.83");
                    }
                });
    }

    private void onEmailTextChanged(CharSequence data) {
        isEmailValid = false;
        if (isEmptyField(data)) {
            getView().clearEmailValidationError();
            return;
        }
        if (validators.get(EMAIL_VALIDATOR).isValid(data)) {
            email = data.toString();
            getView().clearEmailValidationError();
            isEmailValid = true;
            manageButtonStatePresenter();
            return;
        }
        getView().showEmailValidationError(R.string.error_invalid_email);
        manageButtonStatePresenter();
    }

    private void onPasswordTextChanged(CharSequence data) {
        isPasswordValid = false;
        if (isEmptyField(data)) {
            getView().clearPasswordValidationError();
            return;
        }
        if (validators.get(PASSWORD_VALIDATOR).isValid(data)) {
            password = data.toString();
            getView().clearPasswordValidationError();
            isPasswordValid = true;
            manageButtonStatePresenter();
            return;
        }
        getView().showPasswordValidationError(R.string.error_invalid_password);
        manageButtonStatePresenter();
    }

    private boolean isEmptyField(CharSequence data) {
        return validators.get(EMPTY_VALIDATOR).isValid(data);
    }

    private void manageButtonStatePresenter() {
        getView().manageButtonState(isEmailValid && isPasswordValid);
    }

    void handleLocation() {
        String city = getView().getCityLocation();
        if (StringUtils.isNotEmpty(city)) {
            getView().showLocationField(city);
        }
    }

    @Override
    public void detachView() {
        super.detachView();
    }
}
