package com.MeetUp.meetup.screens.splash.dagger;

import com.MeetUp.meetup.screens.splash.SplashPresenter;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public abstract class SplashActivityModule {

    private static final long DELAY = 4 * 1000;

    @Provides
    @SplashActivityScope
    static SplashPresenter providesSplashPresenter() {
        return new SplashPresenter(DELAY);
    }
}
