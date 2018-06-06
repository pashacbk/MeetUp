package com.MeetUp.meetup.di;

import com.MeetUp.meetup.di.module.ApiModule;
import com.MeetUp.meetup.di.module.AppModule;
import com.MeetUp.meetup.di.module.DbModule;
import com.MeetUp.meetup.di.module.GsonModule;
import com.MeetUp.meetup.di.module.RxModule;
import com.MeetUp.meetup.di.module.UtilsModule;
import com.MeetUp.meetup.screens.auth.dagger.AuthActivityComponent;
import com.MeetUp.meetup.screens.auth.modules.intro.dagger.IntroFragmentComponent;
import com.MeetUp.meetup.screens.auth.modules.intro.location.dagger.LocationServiceComponent;
import com.MeetUp.meetup.screens.auth.modules.signin.dagger.SignInComponent;
import com.MeetUp.meetup.screens.group_screen.dagger.GroupInfoActivityComponent;
import com.MeetUp.meetup.screens.home.dagger.HomeActivityComponent;
import com.MeetUp.meetup.screens.splash.dagger.SplashActivityComponent;

import dagger.Component;

@ApplicationScope
@Component(modules = {AppModule.class,
        GsonModule.class,
        ApiModule.class,
        DbModule.class,
        UtilsModule.class,
        RxModule.class})

public interface AppComponent {

    AuthActivityComponent.Builder authActivityBuilder();

    HomeActivityComponent.Builder homeActivityBuilder();

    SignInComponent.Builder signInBuilder();

    IntroFragmentComponent.Builder introFragmentBuilder();

    LocationServiceComponent.Builder locationServiceBuilder();

    SplashActivityComponent.Builder splashActivityBuilder();

    GroupInfoActivityComponent.Builder groupInfoActivityBuilder();
}
