package com.MeetUp.meetup.screens.auth.modules.intro;

import com.MeetUp.meetup.R;
import com.MeetUp.meetup.utils.LocationDataRetriever;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.Status;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

@RunWith(MockitoJUnitRunner.class)
public class IntroFragmentPresenterTest {

    private static final String EMPTY_STRING = "";
    private IntroFragmentPresenter presenter;

    @Mock
    IntroFragmentView view;

    @Before
    public void setUp() throws Exception {
        presenter = new IntroFragmentPresenter();
        Mockito.when(view.isPermissionGranted()).thenReturn(Observable.empty());
        Mockito.when(view.getPermissionResult()).thenReturn(Observable.empty());
        Mockito.when(view.getActivityResult()).thenReturn(Observable.empty());
        Mockito.when(view.onSignInClick()).thenReturn(Observable.empty());
        Mockito.when(view.onSignUpClick()).thenReturn(Observable.empty());
        Mockito.when(view.onLocationUpdates()).thenReturn(Observable.empty());
    }

    @Test
    public void attachViewShouldCallLocationServiceWhenPermissionGranted() {
        Mockito.when(view.isPermissionGranted()).thenReturn(Observable.just(true));
        presenter.attachView(view);
        Mockito.verify(view).startLocationService();
        Mockito.verify(view).showLoading();
    }

    @Test
    public void attachViewShouldRequestPermissionWhenPermissionDenied() {
        Mockito.when(view.isPermissionGranted()).thenReturn(Observable.just(false));
        presenter.attachView(view);
        Mockito.verify(view).requestPermission();
    }

    @Test
    public void onRequestPermissionsResultShouldCallLocationServiceWhenPermissionGranted() {
        Mockito.when(view.getPermissionResult()).thenReturn(Observable.just(true));
        presenter.attachView(view);
        Mockito.verify(view).startLocationService();
        Mockito.verify(view).showLoading();
    }

    @Test
    public void onRequestPermissionsResultShouldCallShowAlertWhenPermissionDenied() {
        Mockito.when(view.getPermissionResult()).thenReturn(Observable.just(false));
        presenter.attachView(view);
        Mockito.verify(view).showError(R.string.location_needed_explanation);
    }

    @Test
    public void onActivityResultResultShouldCallLocationServiceWhenResultOk() {
        Mockito.when(view.getActivityResult()).thenReturn(Observable.just(true));
        presenter.attachView(view);
        Mockito.verify(view).startLocationService();
        Mockito.verify(view).showLoading();
    }

    @Test
    public void onGetLocationShouldHandleErrorWhenErrorOccur() {
        ResolvableApiException resolvableApiException = new ResolvableApiException(new Status(6));
        Mockito.when(view.onLocationUpdates()).thenReturn(Observable.error(resolvableApiException));
        presenter.attachView(view);

        try {
            Mockito.verify(view).checkLocationError(resolvableApiException);
            Mockito.verify(view).hideLoading();
        } catch (Exception expected) {
            Assert.assertEquals(expected, resolvableApiException);
        }
    }

    @Test
    public void onGetLocationShouldRetrieveLocationData() {
        LocationDataRetriever retriever = Mockito.mock(LocationDataRetriever.class);
        Mockito.when(view.onLocationUpdates()).thenReturn(Observable.just(retriever));
        presenter.attachView(view);
        Mockito.verify(view).hideLoading();
    }

    @Test
    public void onSignInClickShouldShowSignInScreen() {
        Mockito.when(view.onSignInClick()).thenReturn(Observable.just(Mockito.mock(Object.class)));
        presenter.attachView(view);
        Mockito.verify(view).showSignIn(EMPTY_STRING, EMPTY_STRING);
    }

    @Test
    public void onSignUpClickShouldShowSignUpScreen() {
        Mockito.when(view.onSignUpClick()).thenReturn(Observable.just(Mockito.mock(Object.class)));
        presenter.attachView(view);
        Mockito.verify(view).showSignUp();
    }

}