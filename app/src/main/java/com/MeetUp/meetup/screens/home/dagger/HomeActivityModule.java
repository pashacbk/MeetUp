package com.MeetUp.meetup.screens.home.dagger;

import android.content.res.Resources;
import android.support.v7.widget.LinearLayoutManager;
import android.util.SparseArray;

import com.MeetUp.meetup.R;
import com.MeetUp.meetup.api.GroupsApi;
import com.MeetUp.meetup.api.LocationApi;
import com.MeetUp.meetup.api.TopicsApi;
import com.MeetUp.meetup.data.assets.User;
import com.MeetUp.meetup.data.db.room.dao.GroupDao;
import com.MeetUp.meetup.data.db.room.dao.TopicDao;
import com.MeetUp.meetup.screens.home.HomeActivity;
import com.MeetUp.meetup.screens.home.HomeActivityModel;
import com.MeetUp.meetup.screens.home.HomeActivityPresenter;
import com.MeetUp.meetup.screens.home.adapters.GroupListAdapter;
import com.MeetUp.meetup.utils.ApiErrorHandler;
import com.MeetUp.meetup.utils.NetworkUtils;
import com.MeetUp.meetup.utils.rx.AppRxSchedulers;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

import static com.MeetUp.meetup.screens.home.HomeActivity.USER_KEY;

@Module
public class HomeActivityModule {

    private HomeActivity activity;

    public HomeActivityModule(HomeActivity activity) {
        this.activity = activity;
    }

    @Provides
    @HomeActivityScope
    HomeActivityPresenter providesPresenter(HomeActivityModel model) {
        return new HomeActivityPresenter(model);
    }

    @Provides
    @HomeActivityScope
    HomeActivityModel providesHomeActivityModel(AppRxSchedulers rxSchedulers,
                                                User user,
                                                GroupsApi groupsApi,
                                                TopicsApi topicsApi,
                                                LocationApi locationApi,
                                                NetworkUtils networkUtils,
                                                ApiErrorHandler errorHandler,
                                                GroupDao groupDao,
                                                TopicDao topicDao) {
        return new HomeActivityModel(rxSchedulers, user,
                groupsApi, topicsApi, locationApi, networkUtils, errorHandler, groupDao, topicDao);
    }

    @Provides
    @HomeActivityScope
    User providesUser(HomeActivity activity) {
        return activity.getIntent().getParcelableExtra(USER_KEY);
    }

    @Provides
    @HomeActivityScope
    LinearLayoutManager providesManager(HomeActivity activity) {
        return new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
    }

    @Provides
    @HomeActivityScope
    HomeActivity providesActivity() {
        return activity;
    }


    @Provides
    @HomeActivityScope
    ApiErrorHandler providesApiErrorHandler(SparseArray<String> errorMap, Gson gson) {
        return new ApiErrorHandler(errorMap, gson);
    }

    @Provides
    @HomeActivityScope
    SparseArray<String> providesErrorMap(HomeActivity context) {
        Resources resources = context.getResources();
        SparseArray<String> map = new SparseArray<>();
        int[] errorCodes = resources.getIntArray(R.array.error_code);
        String[] errorMessages = resources.getStringArray(R.array.error_message);
        for (int i = 0; i < errorCodes.length; i++) {
            map.put(errorCodes[i], errorMessages[i]);
        }
        return map;
    }

    @Provides
    @HomeActivityScope
    GroupListAdapter providesAdapter() {
        return new GroupListAdapter();
    }
}
