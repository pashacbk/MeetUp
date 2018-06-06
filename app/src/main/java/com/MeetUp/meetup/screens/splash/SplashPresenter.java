package com.MeetUp.meetup.screens.splash;


import com.MeetUp.meetup.utils.base.BasePresenter;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class SplashPresenter extends BasePresenter<SplashView> {

    private final long delay;

    public SplashPresenter(long delay) {
        this.delay = delay;
    }

    @Override
    protected void onViewAttached() {
        super.onViewAttached();
        addDisposable(startCountDown());
    }

    private Disposable startCountDown() {
        return Completable
                .timer(delay, TimeUnit.MILLISECONDS)
                .subscribe(getView()::startIntroActivity);
    }
}

