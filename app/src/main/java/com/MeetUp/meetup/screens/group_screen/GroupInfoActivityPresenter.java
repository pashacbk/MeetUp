package com.MeetUp.meetup.screens.group_screen;

import com.MeetUp.meetup.R;
import com.MeetUp.meetup.data.db.room.entities.Group;
import com.MeetUp.meetup.utils.base.BasePresenter;
import com.MeetUp.meetup.utils.rx.AppRxSchedulers;

import io.reactivex.disposables.Disposable;

public class GroupInfoActivityPresenter extends BasePresenter<GroupInfoActivityView> {

    private static final String GROUP_STATUS_STR = "Group Status: ";
    private static final String GROUP_MEMBERS_STR1 = "We are ";
    private static final String GROUP_MEMBERS_STR2 = " members here!";
    private final GroupInfoActivityModel model;
    private final AppRxSchedulers rxSchedulers;

    public GroupInfoActivityPresenter(GroupInfoActivityModel model,
                                      AppRxSchedulers rxSchedulers) {
        this.model = model;
        this.rxSchedulers = rxSchedulers;
    }

    @Override
    protected void onViewAttached() {
        super.onViewAttached();
        addDisposable(onSetUpView());
    }

    private Disposable onSetUpView() {
        return model.getGroupFromDb(getView().getGroupId())
                .subscribeOn(rxSchedulers.io())
                .observeOn(rxSchedulers.androidUI())
                .subscribe(group -> {
                           setUpView(group);
                        },
                        throwable -> getView().showError(R.string.error_api_not_found));
    }

    private void setUpView(Group group) {
        getView().setCollapsingToolbarTitle(group.getUrlname());
        setImageGroup(group);
        getView().setGroupMembers(GROUP_MEMBERS_STR1 + group.getMembers() + GROUP_MEMBERS_STR2);
        getView().setGroupDescription(group.getDescription());
        getView().setGroupStatus(GROUP_STATUS_STR + group.getStatus());
    }

    private void setImageGroup(Group group) {
        if (group.getKeyPhoto() != null) {
            getView().setGroupImageFromNetwork(group.getKeyPhoto().getHighresLink());
        } else {
            getView().setGroupImageDefault();
        }
    }
}
