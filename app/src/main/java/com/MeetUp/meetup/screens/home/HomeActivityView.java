package com.MeetUp.meetup.screens.home;

import com.MeetUp.meetup.api.models.LocationModel;
import com.MeetUp.meetup.screens.home.adapters.SingleGroupModel;
import com.MeetUp.meetup.utils.base.BaseView;

import java.util.List;

import io.reactivex.Observable;


public interface HomeActivityView extends BaseView {

    Observable<Object> onFabButtonClick();
    Observable<String> onSearchTextChanged();
    Observable<Object> onSwipeRefresh();
    Observable<LocationModel> onSuggestionListener();
    Observable<Object> onShowDataFromDb();
    void updateGroupCards(List<SingleGroupModel> data);
    void showAlertWithChoice(String message);
    void showAlert(String message);
    void showMessage(int message);
    void showLoading();
    void hideLoading();
    void fillSearchList(List<LocationModel> locations);
    void hideLoadingSwipeRefresh();
    String getLatitude();
    String getLongitude();
    Observable<Integer> onGroupClick();
    void startGroupInfoActivity(int groupId);
}
