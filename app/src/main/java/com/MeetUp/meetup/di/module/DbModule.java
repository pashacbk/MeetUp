package com.MeetUp.meetup.di.module;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.MeetUp.meetup.data.db.room.MeetUpDb;
import com.MeetUp.meetup.data.db.room.dao.GroupDao;
import com.MeetUp.meetup.data.db.room.dao.TopicDao;
import com.MeetUp.meetup.di.ApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public class DbModule {

    private static final String DB_NAME = "MeetUpDataBase";

    @Provides
    @ApplicationScope
    MeetUpDb providesMeetUpDb(Context context) {
        return Room
                .databaseBuilder(context, MeetUpDb.class, DB_NAME)
                .build();
    }

    @Provides
    @ApplicationScope
    TopicDao providesTopicDao(MeetUpDb meetUpDb) {
        return meetUpDb.topicDao();
    }

    @Provides
    @ApplicationScope
    GroupDao providesGroupDao(MeetUpDb meetUpDb) {
        return meetUpDb.groupDao();
    }
}
