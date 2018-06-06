package com.MeetUp.meetup.screens.auth.modules.signin;

import com.MeetUp.meetup.R;
import com.MeetUp.meetup.utils.validate.Validator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;

import static com.MeetUp.meetup.screens.auth.modules.signin.dagger.SignInModule.EMAIL_VALIDATOR;
import static com.MeetUp.meetup.screens.auth.modules.signin.dagger.SignInModule.EMPTY_VALIDATOR;
import static com.MeetUp.meetup.screens.auth.modules.signin.dagger.SignInModule.PASSWORD_VALIDATOR;

@RunWith(MockitoJUnitRunner.class)
public class SignInPresenterTest {

    private static final String EMPTY_STRING = "";
    private static final String DEFAULT_CITY = "Chisinau";
    private static final int DEFAULT_ERROR_CODE = -1;
    private static final String EMAIL = "a@a.a";
    private static final String PASSWORD = "12345678";
    private Map<String, Validator<CharSequence>> validators;
    private SignInPresenter presenter;

    @Mock
    SignInView view;
    @Mock
    Validator<CharSequence> emailValidator;
    @Mock
    Validator<CharSequence> passwordValidator;
    @Mock
    Validator<CharSequence> emptyValidator;
    @Mock
    SignInModel model;

    @Before
    public void setUp() throws Exception {
        validators = new HashMap<>();
        validators.put(EMAIL_VALIDATOR, emailValidator);
        validators.put(PASSWORD_VALIDATOR, passwordValidator);
        validators.put(EMPTY_VALIDATOR, emptyValidator);
        presenter = new SignInPresenter(validators, model);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(__->Schedulers.trampoline());
        Mockito.when(view.setSignInButtonClickListener()).thenReturn(Observable.empty());
        Mockito.when(view.setEmailTextListener()).thenReturn(Observable.empty());
        Mockito.when(view.setPasswordTextListener()).thenReturn(Observable.empty());
        presenter.attachView(view);
    }

    @Test
    public void onEmailTextChangedShouldClearValidationErrorWhenDataEmpty() {

        CharSequence data = Mockito.mock(CharSequence.class);
        Mockito.when(view.setEmailTextListener()).thenReturn(Observable.just(data));
        Mockito.when(validators.get(EMPTY_VALIDATOR).isValid(data)).thenReturn(true);
        presenter.onViewAttached();
        Mockito.verify(view).clearEmailValidationError();
    }

    @Test
    public void onEmailTextChangedShouldShowErrorWhenInvalidData() {
        CharSequence data = Mockito.mock(CharSequence.class);
        Mockito.when(view.setEmailTextListener()).thenReturn(Observable.just(data));
        Mockito.when(validators.get(EMPTY_VALIDATOR).isValid(data)).thenReturn(false);
        Mockito.when(validators.get(EMAIL_VALIDATOR).isValid(data)).thenReturn(false);
        presenter.onViewAttached();
        Mockito.verify(view).showEmailValidationError(R.string.error_invalid_email);
        Mockito.verify(view).manageButtonState(false);
    }

    @Test
    public void onEmailTextChangedShouldClearValidationErrorWhenValidData() {
        CharSequence data = Mockito.mock(CharSequence.class);
        Mockito.when(view.setEmailTextListener()).thenReturn(Observable.just(data));
        Mockito.when(validators.get(EMPTY_VALIDATOR).isValid(data)).thenReturn(false);
        Mockito.when(validators.get(EMAIL_VALIDATOR).isValid(data)).thenReturn(true);
        presenter.onViewAttached();
        Mockito.verify(view).clearEmailValidationError();
        Mockito.verify(view).manageButtonState(false);
    }

    @Test
    public void onPasswordTextChangedShouldClearValidationErrorWhenDataEmpty() {
        CharSequence data = Mockito.mock(CharSequence.class);
        Mockito.when(view.setPasswordTextListener()).thenReturn(Observable.just(data));
        Mockito.when(validators.get(EMPTY_VALIDATOR).isValid(data)).thenReturn(true);
        presenter.onViewAttached();
        Mockito.verify(view).clearPasswordValidationError();
    }

    @Test
    public void onPasswordTextChangedShouldShowErrorWhenInvalidData() {
        CharSequence data = Mockito.mock(CharSequence.class);
        Mockito.when(view.setPasswordTextListener()).thenReturn(Observable.just(data));
        Mockito.when(validators.get(EMPTY_VALIDATOR).isValid(data)).thenReturn(false);
        Mockito.when(validators.get(PASSWORD_VALIDATOR).isValid(data)).thenReturn(false);
        presenter.onViewAttached();
        Mockito.verify(view).showPasswordValidationError(R.string.error_invalid_password);
        Mockito.verify(view).manageButtonState(false);
    }

    @Test
    public void onPasswordTextChangedShouldClearValidationErrorWhenValidData() {
        CharSequence data = Mockito.mock(CharSequence.class);
        Mockito.when(view.setPasswordTextListener()).thenReturn(Observable.just(data));
        Mockito.when(validators.get(EMPTY_VALIDATOR).isValid(data)).thenReturn(false);
        Mockito.when(validators.get(PASSWORD_VALIDATOR).isValid(data)).thenReturn(true);
        presenter.onViewAttached();
        Mockito.verify(view).clearPasswordValidationError();
        Mockito.verify(view).manageButtonState(false);
    }

    @Test
    public void handleLocationShouldShowUserLocationWhenCityNotNullOrEmpty() {
        Mockito.when(view.getCityLocation()).thenReturn(DEFAULT_CITY);
        presenter.handleLocation();
        Mockito.verify(view).showLocationField(DEFAULT_CITY);
    }
}