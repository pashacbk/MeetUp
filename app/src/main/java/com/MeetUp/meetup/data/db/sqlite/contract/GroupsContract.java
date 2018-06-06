package com.MeetUp.meetup.data.db.sqlite.contract;

import android.provider.BaseColumns;

public final class GroupsContract {

    private GroupsContract() {
    }

    public static class GroupsEntry implements BaseColumns {

        public static final String TABLE_NAME = "groups";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_ID = "group_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_URLNAME = "urlname";
        public static final String COLUMN_CREATED = "created";
        public static final String COLUMN_LAT = "lat";
        public static final String COLUMN_LON = "lon";
        public static final String COLUMN_KEY_PHOTO = "key_photo";
    }
}
