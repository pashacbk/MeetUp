package com.MeetUp.meetup.screens.group_screen.dagger;

import com.MeetUp.meetup.data.db.room.dao.GroupDao;
import com.MeetUp.meetup.screens.group_screen.GroupInfoActivity;
import com.MeetUp.meetup.screens.group_screen.GroupInfoActivityModel;
import com.MeetUp.meetup.screens.group_screen.GroupInfoActivityPresenter;
import com.MeetUp.meetup.utils.rx.AppRxSchedulers;

import dagger.Module;
import dagger.Provides;

@Module
public class GroupInfoActivityModule {

    private GroupInfoActivity activity;

    public GroupInfoActivityModule(GroupInfoActivity activity) {
        this.activity = activity;
    }

    @Provides
    @GroupInfoActivityScope
    GroupInfoActivityPresenter providesPresenter(GroupInfoActivityModel model, AppRxSchedulers rxSchedulers) {
        return new GroupInfoActivityPresenter(model, rxSchedulers);
    }

    @Provides
    @GroupInfoActivityScope
    GroupInfoActivityModel providesGroupInfoActivityModel(GroupDao groupDao) {
        return new GroupInfoActivityModel(groupDao);
    }

    @Provides
    @GroupInfoActivityScope
    GroupInfoActivity providesActivity() {
        return activity;
    }
}
