package com.MeetUp.meetup.screens.auth.dagger;

import com.MeetUp.meetup.screens.auth.AuthActivity;

import dagger.Subcomponent;

@AuthActivityScope
@Subcomponent(modules = AuthActivityModule.class)
public interface AuthActivityComponent {

    @Subcomponent.Builder
    interface Builder {
        AuthActivityComponent build();
    }

    void inject(AuthActivity activity);
}
