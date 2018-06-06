package com.MeetUp.meetup.screens.group_screen.dagger;

import com.MeetUp.meetup.screens.group_screen.GroupInfoActivity;

import dagger.Subcomponent;

@Subcomponent(modules = GroupInfoActivityModule.class)
@GroupInfoActivityScope
public interface GroupInfoActivityComponent {

    @Subcomponent.Builder
    interface Builder {
        GroupInfoActivityComponent.Builder groupInfoActivityModule(GroupInfoActivityModule module);
        GroupInfoActivityComponent build();
    }

    void inject(GroupInfoActivity activity);
}
