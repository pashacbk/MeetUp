package com.MeetUp.meetup.screens.home;

import com.MeetUp.meetup.api.GroupsApi;
import com.MeetUp.meetup.api.LocationApi;
import com.MeetUp.meetup.api.TopicsApi;
import com.MeetUp.meetup.api.response.BaseResponse;
import com.MeetUp.meetup.data.assets.User;
import com.MeetUp.meetup.data.db.room.dao.GroupDao;
import com.MeetUp.meetup.data.db.room.dao.TopicDao;
import com.MeetUp.meetup.data.db.room.entities.Group;
import com.MeetUp.meetup.data.db.room.entities.Topic;
import com.MeetUp.meetup.screens.home.adapters.SingleGroupModel;
import com.MeetUp.meetup.utils.ApiErrorHandler;
import com.MeetUp.meetup.utils.CollectionUtils;
import com.MeetUp.meetup.utils.NetworkUtils;
import com.MeetUp.meetup.utils.rx.AppRxSchedulers;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

import static com.MeetUp.meetup.utils.LogUtils.eLog;

public class HomeActivityModel {

    private static final String TAG = HomeActivityModel.class.getSimpleName();
    private static final int FIRST_ELEMENT = 0;
    private static final String MY_GROUPS = "My Groups";
    private static final int PAGE_COUNT = 8;
    private final User user;
    private final GroupsApi groupsApi;
    private final TopicsApi topicsApi;
    private final LocationApi locationApi;
    private final NetworkUtils networkUtils;
    private final GroupDao groupDao;
    private final TopicDao topicDao;
    private final ApiErrorHandler errorHandler;
    private final AppRxSchedulers rxSchedulers;

    public HomeActivityModel(AppRxSchedulers rxSchedulers,
                             User user,
                             GroupsApi groupsApi,
                             TopicsApi topicsApi,
                             LocationApi locationApi,
                             NetworkUtils networkUtils, ApiErrorHandler errorHandler, GroupDao groupDao,
                             TopicDao topicDao) {
        this.user = user;
        this.groupsApi = groupsApi;
        this.topicsApi = topicsApi;
        this.locationApi = locationApi;
        this.networkUtils = networkUtils;
        this.groupDao = groupDao;
        this.topicDao = topicDao;
        this.errorHandler = errorHandler;
        this.rxSchedulers = rxSchedulers;
    }

    public Single<BaseResponse> fetchData(String lat, String lon) {
        return getUserGroups()
                .flatMap(groups -> getUserTopics())
                .flatMap(Observable::fromIterable)
                .flatMap(topic -> getUserGroupsByTopicId(topic.getId(), lat, lon))
                .toList()
                .flatMap(groups -> getGroupModelListFromDb(lat, lon))
                .map(models -> mapBaseResponse(models, new BaseResponse()))
                .onErrorResumeNext(throwable -> Single.just(handleError(throwable, new BaseResponse())));
    }

    private Observable<List<Group>> getUserGroups() {
        return networkUtils.isNetworkAvailableRx()
                .observeOn(rxSchedulers.io())
                .flatMap(isConnected -> groupsApi.getUserGroups(user.getApiKey()))
                .map(groups -> fillGroupList(MY_GROUPS, groups))
                .doOnNext(this::saveGroupsInDb)
                .observeOn(rxSchedulers.androidUI())
                .doOnError(throwable -> eLog(TAG + ": User Groups", throwable.getMessage(), throwable));
    }

    private Observable<List<Topic>> getUserTopics() {
        return networkUtils.isNetworkAvailableRx()
                .observeOn(rxSchedulers.io())
                .flatMap(isConnected -> topicsApi.getUserTopics(user.getMemberId(), user.getApiKey()))
                .doOnNext(topics -> topics.add(FIRST_ELEMENT, getMyGroupsDefaultTopic()))
                .doOnNext(this::saveTopicsInDb)
                .observeOn(rxSchedulers.androidUI())
                .doOnError(throwable -> eLog(TAG + ": Topics", throwable.getMessage(), throwable));
    }

    private Observable<List<Group>> getUserGroupsByTopicId(String topicId, String lat, String lon) {
        return networkUtils.isNetworkAvailableRx()
                .observeOn(rxSchedulers.io())
                .filter(isConnected -> !topicId.equals(MY_GROUPS))
                .flatMap(isConnected -> groupsApi.getGroupsByTopicId(user.getApiKey(), lon, lat, topicId, PAGE_COUNT))
                .map(groups -> fillGroupList(topicId, groups))
                .doOnNext(this::saveGroupsInDb)
                .observeOn(rxSchedulers.androidUI())
                .doOnError(throwable -> eLog(TAG + ": Groups by topicId", throwable.getMessage(), throwable));
    }

    public Observable<BaseResponse> findLocations(String query) {
        return networkUtils.isNetworkAvailableRx()
                .observeOn(rxSchedulers.io())
                .flatMap(aBoolean -> locationApi.getLocations(user.getApiKey(), query))
                .observeOn(rxSchedulers.androidUI())
                .map(locationModels -> mapBaseResponse(locationModels, new BaseResponse()))
                .onErrorResumeNext(throwable -> {
                    return Observable.just(handleError(throwable, new BaseResponse()));
                });
    }

    private BaseResponse handleError(Throwable throwable, BaseResponse response) {
        response.setSuccess(false);
        response.setErrorMessage(errorHandler.handleError(throwable));
        return response;
    }

    private <T> BaseResponse mapBaseResponse(T t, BaseResponse response) {
        response.setSuccess(false);
        if (t != null) {
            response.setData(t);
            response.setSuccess(true);
        }
        return response;
    }

    private List<SingleGroupModel> fillGroupModelList(List<Group> groups, List<Topic> topics) {
        List<SingleGroupModel> singleGroupModels = new ArrayList<>();
        for (Topic topic : topics) {
            List<Group> topicGroup = new ArrayList<>();
            for (Group group : groups) {
                if (group.getTopicId().equals(topic.getId())) {
                    topicGroup.add(group);
                }
            }
            if (CollectionUtils.isNotEmpty(topicGroup)) {
                SingleGroupModel groupModel = new SingleGroupModel(topic.getName(), topicGroup);
                singleGroupModels.add(groupModel);
            }
        }
        return singleGroupModels;
    }

    public Single<List<SingleGroupModel>> getGroupModelListFromDb(String lat, String lon) {
        return Single.just(new Object())
                .observeOn(rxSchedulers.io())
                .flatMap(o -> getTopicsFromDb())
                .flatMap(topics -> getGroupsByLocation(lat, lon)
                        .map(groups -> fillGroupModelList(groups, topics)))
                .observeOn(rxSchedulers.androidUI());
    }

    private Topic getMyGroupsDefaultTopic() {
        Topic topic = new Topic();
        topic.setId(MY_GROUPS);
        topic.setName(MY_GROUPS);
        return topic;
    }

    private List<Group> fillGroupList(String topicId, List<Group> data) {
        for (Group group : data) {
            group.setTopicId(topicId);
        }
        return data;
    }

    private void saveTopicsInDb(List<Topic> data) {
        topicDao.delete(data);
        topicDao.insertAll(data);
    }

    private void saveGroupsInDb(List<Group> data) {
        groupDao.delete(data);
        groupDao.insertAll(data);
    }

    private Single<List<Group>> getGroupsByLocation(String lat, String lon) {
        return groupDao.loadByCoordinates(lat, lon);
    }

    private Single<List<Topic>> getTopicsFromDb() {
        return topicDao.loadAll();
    }
}

