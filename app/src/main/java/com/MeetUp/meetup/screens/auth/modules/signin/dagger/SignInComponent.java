package com.MeetUp.meetup.screens.auth.modules.signin.dagger;

import com.MeetUp.meetup.screens.auth.modules.signin.SignInFragment;

import dagger.Subcomponent;

@SignInScope
@Subcomponent(modules = SignInModule.class)
public interface SignInComponent {

    @Subcomponent.Builder
    interface Builder {
        SignInComponent.Builder signInComponent(SignInModule module);
        SignInComponent build();
    }
    void inject(SignInFragment fragment);
}
