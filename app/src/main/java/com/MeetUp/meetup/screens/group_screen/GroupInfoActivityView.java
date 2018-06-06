package com.MeetUp.meetup.screens.group_screen;

import com.MeetUp.meetup.utils.base.BaseView;

public interface GroupInfoActivityView extends BaseView {
    void setGroupImageFromNetwork(String photoLink);
    void setGroupImageDefault();
    void setGroupMembers(CharSequence groupMembers);
    void setGroupDescription(String groupDescription);
    void setGroupStatus(CharSequence groupStatus);
    void setCollapsingToolbarTitle(CharSequence title);
    int getGroupId();
    void showError(int errorCode);
    void showAlert(CharSequence message);
}
