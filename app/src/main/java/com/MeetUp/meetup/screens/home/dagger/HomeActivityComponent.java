package com.MeetUp.meetup.screens.home.dagger;

import com.MeetUp.meetup.screens.home.HomeActivity;

import dagger.Subcomponent;

@Subcomponent(modules = HomeActivityModule.class)
@HomeActivityScope
public interface HomeActivityComponent {

    @Subcomponent.Builder
    interface Builder {
        HomeActivityComponent.Builder homeActivityModule(HomeActivityModule module);
        HomeActivityComponent build();
    }

    void inject(HomeActivity activity);
}
