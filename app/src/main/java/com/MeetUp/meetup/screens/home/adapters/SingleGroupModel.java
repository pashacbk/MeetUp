package com.MeetUp.meetup.screens.home.adapters;

import com.MeetUp.meetup.data.db.room.entities.Group;

import java.util.List;

public class SingleGroupModel {

    private String nameGroup;
    private List<Group> groups;

    public SingleGroupModel(String nameGroup, List<Group> groups) {
        this.nameGroup = nameGroup;
        this.groups = groups;
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public List<Group> getGroups() {
        return groups;
    }
}
