package com.MeetUp.meetup.screens.auth.modules.intro.dagger;

import com.MeetUp.meetup.screens.auth.modules.intro.IntroFragment;

import dagger.Subcomponent;

@Subcomponent(modules = IntroFragmentModule.class)
@IntroFragmentScope
public interface IntroFragmentComponent {

    @Subcomponent.Builder
    interface Builder {
        IntroFragmentComponent.Builder introFragmentModule(IntroFragmentModule module);
        IntroFragmentComponent build();
    }

    void inject(IntroFragment fragment);
}
