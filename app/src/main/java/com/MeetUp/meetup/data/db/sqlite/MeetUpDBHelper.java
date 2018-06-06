package com.MeetUp.meetup.data.db.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.MeetUp.meetup.data.db.sqlite.contract.GroupsContract;
import com.MeetUp.meetup.data.db.sqlite.contract.KeyPhotoContract;

public class MeetUpDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "meetUp.db";
    private static final int DB_VERSION = 1;
    private static MeetUpDBHelper dbHelper;

    public static MeetUpDBHelper getInstance(Context context) {
        if (dbHelper == null) {
            dbHelper = new MeetUpDBHelper(context);
        }
        return dbHelper;
    }

    private MeetUpDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(getGroupsTable());
        db.execSQL(getGroupPhotoTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + GroupsContract.GroupsEntry.TABLE_NAME);
        db.execSQL("DROP TABLE " + KeyPhotoContract.KeyPhotoEntry.TABLE_NAME);
        onCreate(db);
    }

    private String getGroupsTable() {
        return "CREATE TABLE " + GroupsContract.GroupsEntry.TABLE_NAME + " ("
                + GroupsContract.GroupsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + GroupsContract.GroupsEntry.COLUMN_ID + " INTEGER NOT NULL, "
                + GroupsContract.GroupsEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + GroupsContract.GroupsEntry.COLUMN_LAT + " REAL, "
                + GroupsContract.GroupsEntry.COLUMN_LON + " REAL, "
                + GroupsContract.GroupsEntry.COLUMN_URLNAME + " TEXT NOT NULL, "
                + GroupsContract.GroupsEntry.COLUMN_KEY_PHOTO + " INTEGER, "
                + GroupsContract.GroupsEntry.COLUMN_CREATED + " INTEGER, "
                + "FOREIGN KEY(" + GroupsContract.GroupsEntry.COLUMN_KEY_PHOTO + ") REFERENCES "
                + KeyPhotoContract.KeyPhotoEntry.TABLE_NAME + "(" + KeyPhotoContract.KeyPhotoEntry.COLUMN_ID + "));";
    }

    private String getGroupPhotoTable() {

        return "CREATE TABLE " + KeyPhotoContract.KeyPhotoEntry.TABLE_NAME + " ("
                + KeyPhotoContract.KeyPhotoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KeyPhotoContract.KeyPhotoEntry.COLUMN_BASE_URL + " TEXT, "
                + KeyPhotoContract.KeyPhotoEntry.COLUMN_ID + " INTEGER NOT NULL, "
                + KeyPhotoContract.KeyPhotoEntry.COLUMN_PHOTO_LINK + " TEXT NOT NULL " +
                "DEFAULT \"android.resource://com.endava.meetup/2131230845\", "
                + KeyPhotoContract.KeyPhotoEntry.COLUMN_THUMB_LINK + " TEXT, "
                + KeyPhotoContract.KeyPhotoEntry.COLUMN_TYPE + " INTEGER );";
    }
}
