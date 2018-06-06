package com.MeetUp.meetup.data.db.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.MeetUp.meetup.data.db.room.entities.Group;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface GroupDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<Group> data);

    @Delete
    void delete(List<Group> data);

    @Query("SELECT*FROM groups")
    Single<List<Group>> loadAll();

    @Query("SELECT * FROM groups WHERE groups.topic_id = :topicId")
    Single<List<Group>> loadByTopicId(String topicId);

    @Query("SELECT * FROM groups WHERE groups.lat = :lat AND groups.lon = :lon")
    Single<List<Group>> loadByCoordinates(String lat, String lon);

    @Query("SELECT * FROM groups WHERE groups.id = :groupId")
    Single<Group> loadGroupById(int groupId);

}
