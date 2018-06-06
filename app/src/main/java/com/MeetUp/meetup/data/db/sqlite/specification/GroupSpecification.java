package com.MeetUp.meetup.data.db.sqlite.specification;

import com.MeetUp.meetup.data.db.sqlite.contract.GroupsContract;
import com.MeetUp.meetup.data.db.sqlite.contract.KeyPhotoContract;

public class GroupSpecification implements SqlSpecification {

    @Override
    public String toSqlQuery() {
        return "SELECT * FROM " + GroupsContract.GroupsEntry.TABLE_NAME
                + " JOIN " + KeyPhotoContract.KeyPhotoEntry.TABLE_NAME
                + " ON " + GroupsContract.GroupsEntry.COLUMN_KEY_PHOTO + " = " + KeyPhotoContract.KeyPhotoEntry.COLUMN_ID + ";";
    }
}
