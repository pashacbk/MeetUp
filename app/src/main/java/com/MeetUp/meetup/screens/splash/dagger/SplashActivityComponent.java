package com.MeetUp.meetup.screens.splash.dagger;

import com.MeetUp.meetup.screens.splash.SplashActivity;

import dagger.Subcomponent;

@Subcomponent(modules = SplashActivityModule.class)
@SplashActivityScope
public interface SplashActivityComponent {

    @Subcomponent.Builder
    interface Builder {
        SplashActivityComponent build();
    }

    void inject(SplashActivity activity);

}
