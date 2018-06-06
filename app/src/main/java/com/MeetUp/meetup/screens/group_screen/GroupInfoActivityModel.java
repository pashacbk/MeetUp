package com.MeetUp.meetup.screens.group_screen;

import android.util.Log;

import com.MeetUp.meetup.data.db.room.dao.GroupDao;
import com.MeetUp.meetup.data.db.room.entities.Group;

import io.reactivex.Single;


public class GroupInfoActivityModel {

    public static final String TAG = GroupInfoActivityModel.class.getName();
    private final GroupDao groupDao;

    public GroupInfoActivityModel(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    public Single<Group> getGroupFromDb(int groupId) {
        return groupDao.loadGroupById(groupId)
                .doOnError(throwable -> Log.e(TAG, throwable.getMessage(), throwable));
    }
}
