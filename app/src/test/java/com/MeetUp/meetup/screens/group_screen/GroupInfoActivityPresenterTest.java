package com.MeetUp.meetup.screens.group_screen;

import com.MeetUp.meetup.R;
import com.MeetUp.meetup.data.db.room.entities.Group;
import com.MeetUp.meetup.data.db.room.entities.KeyPhoto;
import com.MeetUp.meetup.utils.rx.TestRxSchedulers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Single;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GroupInfoActivityPresenterTest {

    private static final String EMPTY_STRING = "";
    private static final String GROUP_MEMBERS_STR1 = "We are ";
    private static final String GROUP_MEMBERS_STR2 = " members here!";
    private static final String PHOTO_LINK = "photolink";
    private static final String GROUP_STATUS_STR = "Group Status: ";
    private GroupInfoActivityPresenter presenter;
    private TestRxSchedulers testRxSchedulers;

    @Mock
    GroupInfoActivityView view;
    @Mock
    GroupInfoActivityModel model;

    @Before
    public void setUp() throws Exception {
        testRxSchedulers = new TestRxSchedulers();
        presenter = new GroupInfoActivityPresenter(model, testRxSchedulers);
        when(model.getGroupFromDb(anyInt())).thenReturn(Single.never());

        presenter.attachView(view);
    }

    @Test
    public void onViewAttachedShouldSetUpToolbarTitle() {
        Group group = mock(Group.class);
        when(model.getGroupFromDb(anyInt())).thenReturn(Single.just(group));
        when(group.getUrlname()).thenReturn(EMPTY_STRING);
        presenter.onViewAttached();
        testRxSchedulers.getTestScheduler().triggerActions();
        verify(view).setCollapsingToolbarTitle(eq(EMPTY_STRING));
    }

    @Test
    public void onViewAttachedShouldSetDefaultImage() {
        Group group = mock(Group.class);
        when(model.getGroupFromDb(anyInt())).thenReturn(Single.just(group));
        presenter.onViewAttached();
        testRxSchedulers.getTestScheduler().triggerActions();
        verify(view).setGroupImageDefault();
    }

    @Test
    public void onViewAttachedShouldSetImageFromNetwork() {
        Group group = mock(Group.class);
        when(model.getGroupFromDb(anyInt())).thenReturn(Single.just(group));
        when(group.getKeyPhoto()).thenReturn(mock(KeyPhoto.class));
        when(group.getKeyPhoto().getHighresLink()).thenReturn(PHOTO_LINK);
        presenter.onViewAttached();
        testRxSchedulers.getTestScheduler().triggerActions();
        verify(view).setGroupImageFromNetwork(eq(PHOTO_LINK));
    }

    @Test
    public void onViewAttachedShouldSetGroupMembers() {
        Group group = mock(Group.class);
        when(model.getGroupFromDb(anyInt())).thenReturn(Single.just(group));
        presenter.onViewAttached();
        testRxSchedulers.getTestScheduler().triggerActions();
        verify(view).setGroupMembers(GROUP_MEMBERS_STR1 + group.getMembers() + GROUP_MEMBERS_STR2);
    }

    @Test
    public void onViewAttachedShouldSetGroupStatus() {
        Group group = mock(Group.class);
        when(model.getGroupFromDb(anyInt())).thenReturn(Single.just(group));
        presenter.onViewAttached();
        testRxSchedulers.getTestScheduler().triggerActions();
        verify(view).setGroupStatus(GROUP_STATUS_STR + group.getStatus());
    }

    @Test
    public void onViewAttachedShouldSetGroupDescription() {
        Group group = mock(Group.class);
        when(model.getGroupFromDb(anyInt())).thenReturn(Single.just(group));
        presenter.onViewAttached();
        testRxSchedulers.getTestScheduler().triggerActions();
        verify(view).setGroupDescription(group.getDescription());
    }

    @Test
    public void onViewAttachedShouldDisplayErrorMessage() {
        Exception exception = Mockito.mock(Exception.class);
        when(model.getGroupFromDb(anyInt())).thenReturn(Single.error(exception));
        presenter.onViewAttached();
        testRxSchedulers.getTestScheduler().triggerActions();
        verify(view).showError(R.string.error_api_not_found);
    }
}
