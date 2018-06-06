package com.MeetUp.meetup.screens.home;

import com.MeetUp.meetup.R;
import com.MeetUp.meetup.api.models.LocationModel;
import com.MeetUp.meetup.screens.home.adapters.SingleGroupModel;
import com.MeetUp.meetup.utils.CollectionUtils;
import com.MeetUp.meetup.utils.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;


public class HomeActivityPresenter extends BasePresenter<HomeActivityView> {

    private static final String TAG = HomeActivityPresenter.class.getSimpleName();
    private static final int DELAY_BETWEEN_USER_TAPS = 500;
    private final HomeActivityModel model;
    private String latitude = "47.03";
    private String longitude = "28.83";

    public HomeActivityPresenter(HomeActivityModel model) {
        this.model = model;
    }

    @Override
    protected void onViewAttached() {
        super.onViewAttached();
        latitude = getView().getLatitude();
        longitude = getView().getLongitude();
        addDisposable(getViewData());
        addDisposable(handleQuery());
        addDisposable(refreshSwipeData());
        addDisposable(handleItemSelected());
        addDisposable(onAlertSeeDataClick());
        addDisposable(getOnGroupClick());
    }

    private Disposable handleQuery() {
        return getView().onSearchTextChanged()
                .debounce(DELAY_BETWEEN_USER_TAPS, TimeUnit.MILLISECONDS)
                .flatMap(model::findLocations)
                .filter(response -> {
                    if (!response.isSuccess()) {
                        getView().showAlertWithChoice(response.getErrorMessage());
                        hideLoading();
                    }
                    return response.isSuccess();
                })
                .map(response -> (List<LocationModel>) response.getData())
                .subscribe(nameList -> getView().fillSearchList(nameList),
                        throwable -> hideLoading());
    }

    private Disposable handleItemSelected() {
        return getView().onSuggestionListener()
                .filter(location -> location != null)
                .doOnNext(location -> {
                    latitude = location.getLat();
                    longitude = location.getLon();
                })
                .subscribe(location -> getViewData(),
                        throwable -> hideLoading());
    }

    private Disposable getViewData() {
        getView().showLoading();
        return model.fetchData(latitude, longitude)
                .filter(response -> {
                    if (!response.isSuccess()) {
                        getView().showAlertWithChoice(response.getErrorMessage());
                        hideLoading();
                    }
                    return response.isSuccess();
                })
                .map(response -> (List<SingleGroupModel>) response.getData())
                .filter(models -> {
                    if (CollectionUtils.isEmpty(models)) {
                        getView().showMessage(R.string.no_groups_message);
                        hideLoading();
                    }
                    return CollectionUtils.isNotEmpty(models);
                })
                .subscribe(models -> {
                            getView().updateGroupCards(models);
                            hideLoading();
                        },
                        throwable -> hideLoading());
    }

    private Disposable onAlertSeeDataClick() {
        return getView().onShowDataFromDb()
                .doOnNext(o -> getView().showLoading())
                .flatMap(o -> model.getGroupModelListFromDb(latitude, longitude).toObservable())
                .subscribe(singleGroupModels -> {
                            getView().updateGroupCards(singleGroupModels);
                            hideLoading();
                        },
                        throwable -> hideLoading());
    }

    private Disposable refreshSwipeData() {
        return getView().onSwipeRefresh()
                .subscribe(o -> getViewData());
    }

    private void hideLoading() {
        getView().hideLoading();
        getView().hideLoadingSwipeRefresh();
    }

    private Disposable getOnGroupClick() {
        return getView().onGroupClick()
                .subscribe(integer -> getView().startGroupInfoActivity(integer));
    }

}
