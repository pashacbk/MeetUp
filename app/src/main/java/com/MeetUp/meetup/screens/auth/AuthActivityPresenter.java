package com.MeetUp.meetup.screens.auth;


import com.MeetUp.meetup.utils.base.BasePresenter;

public class AuthActivityPresenter extends BasePresenter<AuthActivityView> {

    @Override
    protected void onViewAttached() {
        super.onViewAttached();
        getView().showFragment();
    }
}
