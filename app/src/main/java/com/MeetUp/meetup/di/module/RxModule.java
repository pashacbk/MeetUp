package com.MeetUp.meetup.di.module;


import com.MeetUp.meetup.di.ApplicationScope;
import com.MeetUp.meetup.utils.rx.AppRxSchedulers;

import dagger.Module;
import dagger.Provides;

@Module
public class RxModule {

    @Provides
    @ApplicationScope
    AppRxSchedulers providesRxScheduler() {
        return new AppRxSchedulers();
    }
}
