package com.MeetUp.meetup.data.db.sqlite.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.MeetUp.meetup.data.db.room.entities.Group;
import com.MeetUp.meetup.data.db.sqlite.contract.GroupsContract;
import com.MeetUp.meetup.data.db.sqlite.contract.KeyPhotoContract;
import com.MeetUp.meetup.data.db.sqlite.mapper.GroupsMapper;
import com.MeetUp.meetup.data.db.sqlite.specification.SqlSpecification;

import java.util.ArrayList;
import java.util.List;

public class GroupsRepository implements BaseRepository<Group> {

    private final SQLiteOpenHelper openHelper;

    public GroupsRepository(SQLiteOpenHelper openHelper) {
        this.openHelper = openHelper;
    }

    @Override
    public void add(Group item) {
    }

    @Override
    public void add(List<Group> items) {
        SQLiteDatabase database = openHelper.getWritableDatabase();
        database.beginTransaction();
        for (Group item : items) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(GroupsContract.GroupsEntry.COLUMN_ID, item.getId());
            contentValues.put(GroupsContract.GroupsEntry.COLUMN_NAME, item.getName());
            contentValues.put(GroupsContract.GroupsEntry.COLUMN_URLNAME, item.getUrlname());
            contentValues.put(GroupsContract.GroupsEntry.COLUMN_CREATED, item.getCreated());
            contentValues.put(GroupsContract.GroupsEntry.COLUMN_LAT, item.getLat());
            contentValues.put(GroupsContract.GroupsEntry.COLUMN_LON, item.getLon());
            contentValues.put(GroupsContract.GroupsEntry.COLUMN_KEY_PHOTO, item.getKeyPhoto().getId());
            long row = database.insert(GroupsContract.GroupsEntry.TABLE_NAME, null, contentValues);
            Log.d("GroupRepo", "RowId = " + row);
            ContentValues contentValues1 = new ContentValues();
            contentValues1.put(KeyPhotoContract.KeyPhotoEntry.COLUMN_ID, item.getKeyPhoto().getId());
            contentValues1.put(KeyPhotoContract.KeyPhotoEntry.COLUMN_BASE_URL, item.getKeyPhoto().getBaseUrl());
            contentValues1.put(KeyPhotoContract.KeyPhotoEntry.COLUMN_PHOTO_LINK, item.getKeyPhoto().getPhotoLink());
            contentValues1.put(KeyPhotoContract.KeyPhotoEntry.COLUMN_THUMB_LINK, item.getKeyPhoto().getThumbLink());
            contentValues1.put(KeyPhotoContract.KeyPhotoEntry.COLUMN_TYPE, item.getKeyPhoto().getType());
            long newRowId = database.insert(KeyPhotoContract.KeyPhotoEntry.TABLE_NAME, null, contentValues1);
        }
        database.setTransactionSuccessful();
        database.endTransaction();
        database.close();
    }

    @Override
    public void update(Group item) {

        //todo should be implemented
    }

    @Override
    public void remove(Group item) {
        //todo should be implemented
    }

    @Override
    public List<Group> query(SqlSpecification specification) {
        GroupsMapper mapper = new GroupsMapper();
        List<Group> groups = new ArrayList<>();
        SQLiteDatabase database = openHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery(specification.toSqlQuery(), null);
        while (cursor.moveToNext()) {
            groups.add(mapper.map(cursor));
        }
        cursor.close();
        database.close();
        return groups;
    }
}
