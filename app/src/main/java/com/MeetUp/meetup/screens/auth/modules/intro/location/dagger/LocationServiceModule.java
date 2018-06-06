package com.MeetUp.meetup.screens.auth.modules.intro.location.dagger;

import com.MeetUp.meetup.screens.auth.modules.intro.location.LocationHelper;
import com.MeetUp.meetup.screens.auth.modules.intro.location.LocationService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.SettingsClient;

import dagger.Module;
import dagger.Provides;

@Module
public class LocationServiceModule {
    private LocationService service;

    public LocationServiceModule(LocationService service) {
        this.service = service;
    }

    @Provides
    @LocationServiceScope
    LocationHelper providesLocationHelper(FusedLocationProviderClient fusedLocationProviderClient,
                                          LocationService service,
                                          SettingsClient settingsClient) {
        return new LocationHelper(fusedLocationProviderClient, service, settingsClient);
    }

    @Provides
    @LocationServiceScope
    FusedLocationProviderClient providesFusedClient(LocationService service) {
        return LocationServices.getFusedLocationProviderClient(service);
    }

    @Provides
    @LocationServiceScope
    SettingsClient providesSettingsClient(LocationService service) {
        return LocationServices.getSettingsClient(service);
    }

    @Provides
    @LocationServiceScope
    LocationService providesServiceInstance() {
        return service;
    }
}
