package com.MeetUp.meetup.data.db.sqlite.contract;

import android.provider.BaseColumns;

public final class KeyPhotoContract {

    private KeyPhotoContract() {
    }

    public static class KeyPhotoEntry implements BaseColumns {
        public static final String TABLE_NAME = "groupPhoto";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_ID = "key_id";
        public static final String COLUMN_PHOTO_LINK = "photo_link";
        public static final String COLUMN_THUMB_LINK = "thumb_link";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_BASE_URL = "base_url";
    }
}
