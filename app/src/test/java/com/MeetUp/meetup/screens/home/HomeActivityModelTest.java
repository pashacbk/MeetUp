package com.MeetUp.meetup.screens.home;

import com.MeetUp.meetup.api.GroupsApi;
import com.MeetUp.meetup.api.LocationApi;
import com.MeetUp.meetup.api.TopicsApi;
import com.MeetUp.meetup.api.models.LocationModel;
import com.MeetUp.meetup.api.response.BaseResponse;
import com.MeetUp.meetup.data.assets.User;
import com.MeetUp.meetup.data.db.room.dao.GroupDao;
import com.MeetUp.meetup.data.db.room.dao.TopicDao;
import com.MeetUp.meetup.data.db.room.entities.Group;
import com.MeetUp.meetup.data.db.room.entities.Topic;
import com.MeetUp.meetup.screens.home.adapters.SingleGroupModel;
import com.MeetUp.meetup.utils.ApiErrorHandler;
import com.MeetUp.meetup.utils.NetworkUtils;
import com.MeetUp.meetup.utils.rx.TestRxSchedulers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by alexandrsvetenco on 4/20/18.
 */

@RunWith(MockitoJUnitRunner.class)
public class HomeActivityModelTest {

    private static final int DEFAULT_ERROR = -1;
    private static final int FIRST_ELEMENT = 0;
    private static final String DEF_TOPIC_ID = "My Groups";
    private static final String DEFAULT_QUERY = "sample";
    private HomeActivityModel model;
    private List<Topic> topics;
    private List<LocationModel> locations;
    private TestScheduler testScheduler;

    @Mock
    User user;
    @Mock
    ApiErrorHandler errorHandler;
    @Mock
    GroupDao groupDao;
    @Mock
    TopicDao topicDao;
    @Mock
    GroupsApi groupsApi;
    @Mock
    TopicsApi topicsApi;
    @Mock
    LocationApi locationApi;
    @Mock
    NetworkUtils networkUtils;

    @Before
    public void setUp() throws Exception {
        TestRxSchedulers testRxSchedulers = new TestRxSchedulers();
        testScheduler = testRxSchedulers.getTestScheduler();
        model = new HomeActivityModel(testRxSchedulers, user, groupsApi, topicsApi,
                locationApi, networkUtils, errorHandler,
                groupDao, topicDao);
        topics = new ArrayList<>();
        locations = new ArrayList<>();
    }

    @Test
    public void onUserGroupResponseShouldFillGroupsModelWhenResponseNotEmpty() {
        List<Group> data = asList(Mockito.mock(Group.class));
        when(data.get(FIRST_ELEMENT).getTopicId()).thenReturn(DEF_TOPIC_ID);

    }

    @Test
    public void fetchData() {
        List<SingleGroupModel> models = asList(Mockito.mock(SingleGroupModel.class));
        BaseResponse response = new BaseResponse();
        response.setSuccess(true);
        response.setData(models);
        when(networkUtils.isNetworkAvailableRx()).thenReturn(Observable.just(true));
        when(groupsApi.getUserGroups(user.getApiKey())).thenReturn(Observable.just((asList(mock(Group.class)))));
        when(topicsApi.getUserTopics(user.getMemberId(), user.getApiKey())).thenReturn(Observable.just(asList(mock(Topic.class))));
        when(groupsApi.getGroupsByTopicId(user.getApiKey(),
                "11", "21", "11", 1)).thenReturn(Observable.just(asList(mock(Group.class))));
      //  when(model.fetchData("11", "21")).thenReturn(Single.just(response));
        model.fetchData("11", "21");

    }

    @Test
    public void findLocations() {
        LocationModel locationModel = mock(LocationModel.class);
        List<LocationModel> list = asList(locationModel);
        when(networkUtils.isNetworkAvailableRx()).thenReturn(Observable.just(true));
        when(locationApi.getLocations(user.getApiKey(), "12")).thenReturn(Observable.just(list));
        model.findLocations("12");
    }

    @Test
    public void getGroupsByTopicIfNeededShouldCallApiWhenThereIsUnusedTopics() {

    }

    @Test
    public void onLocationsResponseShouldCallOnLocationDropDownList() {

    }

    @Test
    public void onUserTopicsResponseShouldCallOnSuccessWhenResponseIsEmpty() {

    }

    @Test
    public void onGroupsByTopicIdResponseShouldFillGroupsModelWhenResponseNotEmpty() {

    }

    @Test
    public void onErrorWithResponseBodyShouldCallOnError() {

    }

    @Test
    public void onErrorWithResponseThrowableShouldCallOnError() {

    }

    @Test
    public void tryToUseDbDataShouldCallOnGroupsUpdateWhenDbIsNotEmpty() {

    }

    @Test
    public void tryToUseDbDataShouldCallOnErrorWhenDbIsEmpty() {

    }

    @Test
    public void fillGroupModelListShouldFillGroupModelListWhenTopicIdIsEqual() {

    }

    @Test
    public void findLocationsShouldCallGetLocationsFromNetwork() {

    }

    @Test
    public void cancelApiCallsShouldCancelApiCalls() {

    }
}