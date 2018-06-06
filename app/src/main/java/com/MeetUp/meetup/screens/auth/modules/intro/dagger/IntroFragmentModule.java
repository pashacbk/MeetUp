package com.MeetUp.meetup.screens.auth.modules.intro.dagger;

import android.location.Geocoder;

import com.MeetUp.meetup.screens.auth.AuthActivity;
import com.MeetUp.meetup.screens.auth.modules.intro.IntroFragment;
import com.MeetUp.meetup.screens.auth.modules.intro.IntroFragmentPresenter;

import java.util.Locale;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class IntroFragmentModule {
    private final IntroFragment fragment;

    public IntroFragmentModule(IntroFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @IntroFragmentScope
    IntroFragmentPresenter providesPresenter() {
        return new IntroFragmentPresenter();
    }

    @Provides
    @IntroFragmentScope
    Geocoder providesGeocoder() {
        return new Geocoder(fragment.getActivity(), Locale.getDefault());
    }
}