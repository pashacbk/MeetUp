package com.MeetUp.meetup.screens.auth.modules.intro.location.dagger;

import com.MeetUp.meetup.screens.auth.modules.intro.location.LocationService;

import dagger.Subcomponent;

@Subcomponent(modules = LocationServiceModule.class)
@LocationServiceScope
public interface LocationServiceComponent {

    @Subcomponent.Builder
    interface Builder {
        LocationServiceComponent.Builder locationServiceComponent(LocationServiceModule module);
        LocationServiceComponent build();
    }
    void inject(LocationService service);
}
