package com.MeetUp.meetup.screens.auth.dagger;

import com.MeetUp.meetup.screens.auth.AuthActivityPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class AuthActivityModule {

    @Provides
    @AuthActivityScope
    static AuthActivityPresenter providesPresenter() {
        return new AuthActivityPresenter();
    }

}
