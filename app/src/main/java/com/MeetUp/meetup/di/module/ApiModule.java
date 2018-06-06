package com.MeetUp.meetup.di.module;

import com.MeetUp.meetup.api.GroupsApi;
import com.MeetUp.meetup.api.LocationApi;
import com.MeetUp.meetup.api.TopicsApi;
import com.MeetUp.meetup.di.ApplicationScope;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    private static final String BASE_URL = "https://api.meetup.com";

    @Provides
    @ApplicationScope
    Retrofit providesRetrofitClient(Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @ApplicationScope
    TopicsApi providesTopicApi(Retrofit retrofit) {
        return retrofit.create(TopicsApi.class);
    }

    @Provides
    @ApplicationScope
    LocationApi providesLocationApi(Retrofit retrofit) {
        return retrofit.create(LocationApi.class);
    }

    @Provides
    @ApplicationScope
    GroupsApi providesGroupsApi(Retrofit retrofit) {
        return retrofit.create(GroupsApi.class);
    }


}
