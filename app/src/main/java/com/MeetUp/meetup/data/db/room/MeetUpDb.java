package com.MeetUp.meetup.data.db.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.MeetUp.meetup.data.db.room.dao.GroupDao;
import com.MeetUp.meetup.data.db.room.dao.TopicDao;
import com.MeetUp.meetup.data.db.room.entities.Group;
import com.MeetUp.meetup.data.db.room.entities.Topic;

@Database(entities = {Group.class, Topic.class}, version = 1)
public abstract class MeetUpDb extends RoomDatabase {
    public abstract GroupDao groupDao();
    public abstract TopicDao topicDao();
}
