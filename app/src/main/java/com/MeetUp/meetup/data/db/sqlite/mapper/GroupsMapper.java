package com.MeetUp.meetup.data.db.sqlite.mapper;

import android.database.Cursor;

import com.MeetUp.meetup.data.db.room.entities.Group;
import com.MeetUp.meetup.data.db.room.entities.KeyPhoto;
import com.MeetUp.meetup.data.db.sqlite.contract.GroupsContract;
import com.MeetUp.meetup.data.db.sqlite.contract.KeyPhotoContract;

public class GroupsMapper {

    public Group map(Cursor cursor) {
        Group group = new Group();
        group.setId(cursor.getInt(cursor.getColumnIndex(GroupsContract.GroupsEntry.COLUMN_ID)));
        group.setName(cursor.getString(cursor.getColumnIndex(GroupsContract.GroupsEntry.COLUMN_NAME)));
        group.setUrlname(cursor.getString(cursor.getColumnIndex(GroupsContract.GroupsEntry.COLUMN_URLNAME)));
        group.setCreated(cursor.getLong(cursor.getColumnIndex(GroupsContract.GroupsEntry.COLUMN_CREATED)));
        group.setLat(cursor.getString(cursor.getColumnIndex(GroupsContract.GroupsEntry.COLUMN_LAT)));
        group.setLon(cursor.getString(cursor.getColumnIndex(GroupsContract.GroupsEntry.COLUMN_LON)));
        KeyPhoto keyPhoto = new KeyPhoto();
        keyPhoto.setId(cursor.getInt(cursor.getColumnIndex(KeyPhotoContract.KeyPhotoEntry.COLUMN_ID)));
        keyPhoto.setPhotoLink(cursor.getString(cursor.getColumnIndex(KeyPhotoContract.KeyPhotoEntry.COLUMN_PHOTO_LINK)));
        keyPhoto.setThumbLink(cursor.getString(cursor.getColumnIndex(KeyPhotoContract.KeyPhotoEntry.COLUMN_THUMB_LINK)));
        keyPhoto.setType(cursor.getString(cursor.getColumnIndex(KeyPhotoContract.KeyPhotoEntry.COLUMN_TYPE)));
        keyPhoto.setBaseUrl(cursor.getString(cursor.getColumnIndex(KeyPhotoContract.KeyPhotoEntry.COLUMN_BASE_URL)));
        group.setKeyPhoto(keyPhoto);
        return group;
    }
}
