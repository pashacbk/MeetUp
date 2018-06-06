package com.MeetUp.meetup.data.db.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.MeetUp.meetup.data.db.room.entities.Group;
import com.MeetUp.meetup.data.db.room.entities.Topic;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface TopicDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<Topic> data);

    @Delete
    void delete(List<Topic> data);

    @Query("SELECT * FROM topics")
    Single<List<Topic>> loadAll();
}
