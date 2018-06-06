package com.MeetUp.meetup.screens.home;

import com.MeetUp.meetup.api.models.LocationModel;
import com.MeetUp.meetup.api.response.BaseResponse;
import com.MeetUp.meetup.screens.auth.modules.intro.location.LocationHelper;
import com.MeetUp.meetup.screens.home.adapters.SingleGroupModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class HomeActivityPresenterTest {

    private static final String SOME_ERROR = "some_error";
    private static final String DEFAULT_LAT = "47.03";
    private static final String DEFAULT_LON = "28.83";
    private String latitude;
    private String longitude;
    private HomeActivityPresenter presenter;

    @Mock
     HomeActivityView view;

    @Mock
     HomeActivityModel model;

    @Before
    public void setUp() throws Exception {
        presenter = new HomeActivityPresenter(model);
        when(view.getLatitude()).thenReturn(DEFAULT_LAT);
        when(view.getLongitude()).thenReturn(DEFAULT_LON);
        latitude = view.getLatitude();
        longitude = view.getLongitude();
        when(model.fetchData(latitude, longitude)).thenReturn(Single.never());
        when(view.onSearchTextChanged()).thenReturn(Observable.never());
        when(view.onShowDataFromDb()).thenReturn(Observable.never());
        when(view.onSuggestionListener()).thenReturn(Observable.never());
        when(view.onSwipeRefresh()).thenReturn(Observable.never());
        when(view.onGroupClick()).thenReturn(Observable.never());
    }

    @Test
    public void onGetViewDataShouldUpdateViewWhenResponseWithoutError() {
        List<SingleGroupModel> models = asList(mock(SingleGroupModel.class));
        BaseResponse response = new BaseResponse();
        response.setSuccess(true);
        response.setData(models);
        Mockito.when(model.fetchData(latitude, longitude)).thenReturn(Single.just(response));
        presenter.attachView(view);
        verify(view).showLoading();
        verify(view).hideLoadingSwipeRefresh();
        verify(view).hideLoading();
        verify(view).updateGroupCards(models);
    }

    @Test
    public void onGetViewDataShouldShowErrorWhenResponseError() {
        List<SingleGroupModel> models = asList(mock(SingleGroupModel.class));
        BaseResponse response = new BaseResponse();
        response.setSuccess(false);
        response.setData(models);
        response.setErrorMessage(SOME_ERROR);
        Mockito.when(model.fetchData(latitude, longitude)).thenReturn(Single.just(response));
        Mockito.when(model.fetchData(latitude, longitude)).thenReturn(Single.never());
        Mockito.when(view.onSearchTextChanged()).thenReturn(Observable.never());
        Mockito.when(view.onShowDataFromDb()).thenReturn(Observable.never());
        Mockito.when(view.onSuggestionListener()).thenReturn(Observable.never());
        Mockito.when(view.onSwipeRefresh()).thenReturn(Observable.never());

    }


    @Test
    public void onAlertSeeDataClick() {
        List<SingleGroupModel> models = asList(Mockito.mock(SingleGroupModel.class));
        Mockito.when(view.onShowDataFromDb()).thenReturn(Observable.just(new Object()));
        Mockito.when(model.getGroupModelListFromDb(anyString(), anyString())).thenReturn(Single.just
                (models));
        presenter.attachView(view);
        verify(view).showLoading();
        verify(view).hideLoadingSwipeRefresh();
        verify(view).hideLoading();
        verify(view).showAlertWithChoice(SOME_ERROR);
    }

    @Test
    public void onGetViewDataShouldNotifyUserThatListIsEmptyWhenResponseIsEmpty() {
        List<SingleGroupModel> models = new ArrayList<>();
        BaseResponse response = new BaseResponse();
        response.setSuccess(true);
        response.setData(models);
        Mockito.when(model.fetchData(latitude, longitude)).thenReturn(Single.just(response));
        presenter.attachView(view);
        verify(view).showLoading();
        verify(view).hideLoadingSwipeRefresh();
        verify(view).hideLoading();
        verify(view).showMessage(anyInt());
    }

    @Test
    public void onGetViewDataShouldHideLoadingWhenThrowsUnexpectedError() {
        Exception exception = new Exception();
        Mockito.when(model.fetchData(latitude, longitude)).thenReturn(Single.error(exception));
        presenter.attachView(view);
        try {
            verify(view).hideLoadingSwipeRefresh();
            verify(view).hideLoading();
        } catch (Exception expected) {
            Assert.assertEquals(expected, exception);
        }
    }

    @Test
    public void onAlertSeeDataClickShouldUpdateViewWhenResponseWithoutError() {
        List<SingleGroupModel> models = asList(mock(SingleGroupModel.class));
        when(view.onShowDataFromDb()).thenReturn(Observable.just(new Object()));
        when(model.getGroupModelListFromDb(latitude, longitude)).thenReturn(Single.just(models));
        presenter.attachView(view);
        verify(view).hideLoadingSwipeRefresh();
        verify(view).hideLoading();
        verify(view).updateGroupCards(models);
    }

    @Test
    public void onAlertSeeDataClickShouldHideLoadingWhenThrowsUnexpectedError() {
        Exception exception = new Exception();
        when(view.onShowDataFromDb()).thenReturn(Observable.just(new Object()));
        when(model.getGroupModelListFromDb(latitude, longitude)).thenReturn(Single.error(exception));
        presenter.attachView(view);
        try {
            verify(view).hideLoadingSwipeRefresh();
            verify(view).hideLoading();
        } catch (Exception expected) {
            Assert.assertEquals(expected, exception);
        }
    }


    @Test
    public void handleQueryShouldFillSearchListWhenResponseWithoutError() {
        List<LocationModel> list = asList(mock(LocationModel.class));
        BaseResponse response = new BaseResponse();
        response.setSuccess(true);
        response.setData(list);
        when(view.onSearchTextChanged()).thenReturn(Observable.just(""));
        when(model.findLocations(anyString())).thenReturn(Observable.just(response));
        presenter.attachView(view);
        verify(view).fillSearchList(list);
    }

    @Test
    public void handleQueryShouldShowErrorWhenResponseError() {
        List<LocationModel> list = asList(mock(LocationModel.class));
        BaseResponse response = new BaseResponse();
        response.setSuccess(false);
        response.setErrorMessage(SOME_ERROR);
        response.setData(list);
        when(view.onSearchTextChanged()).thenReturn(Observable.just(""));
        when(model.findLocations(anyString())).thenReturn(Observable.just(response));
        presenter.attachView(view);
        verify(view).showAlertWithChoice(SOME_ERROR);
        verify(view).hideLoadingSwipeRefresh();
        verify(view).hideLoading();
    }

    @Test
    public void handleQueryShouldHideLoadingWhenThrowsUnexpectedError() {
        Exception exception = new Exception();
        when(view.onSearchTextChanged()).thenReturn(Observable.just(""));
        when(model.findLocations(anyString())).thenReturn(Observable.error(exception));
        presenter.attachView(view);
        try {
            verify(view).hideLoadingSwipeRefresh();
            verify(view).hideLoading();
        } catch (Exception expected) {
            Assert.assertEquals(expected, exception);
        }
    }

    @Test
    public void handleItemSelectedShouldRewriteLatLon() {
        LocationModel locationModel = mock(LocationModel.class);
        when(locationModel.getLat()).thenReturn(DEFAULT_LAT);
        when(locationModel.getLon()).thenReturn(DEFAULT_LON);
        when(view.onSuggestionListener()).thenReturn(Observable.just(locationModel));
        presenter.attachView(view);
        latitude = locationModel.getLat();
        longitude = locationModel.getLon();
    }


    @Test
    public void handleItemSelectedShouldHideLoadingWhenThrowsUnexpectedError() {
        Exception exception = new Exception();
        when(view.onSuggestionListener()).thenReturn(Observable.error(exception));
        presenter.attachView(view);
        try {
            verify(view).hideLoadingSwipeRefresh();
            verify(view).hideLoading();
        } catch (Exception expected) {
            Assert.assertEquals(expected, exception);
        }
    }

    @Test
    public void refreshSwipeDataShouldCallFetchData() {
        when(view.onSwipeRefresh()).thenReturn(Observable.just(new Object()));
        presenter.attachView(view);
    }
}