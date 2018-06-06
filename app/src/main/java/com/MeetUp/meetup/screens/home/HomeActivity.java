package com.MeetUp.meetup.screens.home;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.BaseColumns;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.MeetUp.meetup.MeetUpApp;
import com.MeetUp.meetup.R;
import com.MeetUp.meetup.api.models.LocationModel;
import com.MeetUp.meetup.data.assets.User;
import com.MeetUp.meetup.screens.group_screen.GroupInfoActivity;
import com.MeetUp.meetup.screens.home.adapters.GroupListAdapter;
import com.MeetUp.meetup.screens.home.adapters.SingleGroupModel;
import com.MeetUp.meetup.screens.home.dagger.HomeActivityModule;
import com.MeetUp.meetup.utils.base.BaseActivity;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;


public class HomeActivity extends BaseActivity implements HomeActivityView {

    public static final String USER_KEY = "USER_KEY";
    private static final String LATITUDE_KEY = "latitude_key";
    private static final String LONGITUDE_KEY = "longitude_key";
    public static final int CURSOR_ADAPTER_FLAG = 0;
    private AlertDialog dialog;
    private SearchManager searchManager;
    private CursorAdapter suggestionAdapter;
    private String longitude;
    private String latitude;
    private List<LocationModel> locations = new ArrayList<>();
    private PublishSubject<String> searchTextSubject = PublishSubject.create();
    private PublishSubject<Object> swipeRefresh = PublishSubject.create();
    private PublishSubject<LocationModel> onSuggestionClick = PublishSubject.create();
    private PublishSubject<Object> onShowDataFromDbClick = PublishSubject.create();

    @Inject
    HomeActivityPresenter presenter;
    @Inject
    GroupListAdapter groupAdapter;
    @Inject
    LinearLayoutManager linearLayoutManager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fabAddEvent;
    @BindView(R.id.home_host_views)
    RecyclerView mainRecyclerView;
    @BindView(R.id.loader_container)
    ViewGroup loaderContainer;
    @BindView(R.id.searchView_location)
    SearchView searchLocation;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;

    public static void newIntent(Context context, User user, String lat, String lon) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra(USER_KEY, user);
        intent.putExtra(LATITUDE_KEY, lat);
        intent.putExtra(LONGITUDE_KEY, lon);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpToolbar();
        mainRecyclerView.setHasFixedSize(true);
        mainRecyclerView.setLayoutManager(linearLayoutManager);
        mainRecyclerView.setAdapter(groupAdapter);
        setSearchListeners();
        setRefreshSwipe();
        setLocationData(savedInstanceState);
        presenter.attachView(this);
    }

    private void setLocationData(Bundle bundle) {
        if (bundle != null) {
            latitude = bundle.getString(LATITUDE_KEY);
            longitude = bundle.getString(LONGITUDE_KEY);
            return;
        }
        latitude = getIntent().getStringExtra(LATITUDE_KEY);
        longitude = getIntent().getStringExtra(LONGITUDE_KEY);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(LATITUDE_KEY, latitude);
        outState.putString(LONGITUDE_KEY, longitude);
    }

    @Override
    protected int getView() {
        return R.layout.activity_home;
    }

    @Override
    public void setupActivityComponent() {
        MeetUpApp.getAppComponent()
                .homeActivityBuilder()
                .homeActivityModule(new HomeActivityModule(this))
                .build()
                .inject(this);
    }

    private void setSearchListeners() {
        searchLocation.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchTextSubject.onNext(newText);
                return true;
            }
        });
        searchLocation.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                Cursor cursor = searchLocation.getSuggestionsAdapter().getCursor();
                cursor.moveToPosition(position);
                onSuggestionClick.onNext(locations.get(position));
                return true;
            }
        });
    }

    private void setRefreshSwipe() {
        swipeContainer.setOnRefreshListener(() -> swipeRefresh.onNext(new Object()));
        swipeContainer.setProgressBackgroundColorSchemeColor(getResources().
                getColor(R.color.colorPrimary));
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchLocation.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchLocation.setFocusable(false);
        suggestionAdapter = new SimpleCursorAdapter(this,
                R.layout.location_item_sugestion,
                null,
                new String[]{SearchManager.SUGGEST_COLUMN_TEXT_1},
                new int[]{R.id.location_item},
                CURSOR_ADAPTER_FLAG);
        searchLocation.setSuggestionsAdapter(suggestionAdapter);
    }

    @Override
    public void hideLoadingSwipeRefresh() {
        swipeContainer.setRefreshing(false);
    }

    @Override
    public String getLatitude() {
        return latitude;
    }

    @Override
    public String getLongitude() {
        return longitude;
    }

    public Observable<Integer> onGroupClick() {
        return groupAdapter.onGroupClick();
    }

    @Override
    public void startGroupInfoActivity(int groupId) {
        startActivity(GroupInfoActivity.newIntent(this, groupId));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public Observable<Object> onFabButtonClick() {
        return RxView.clicks(fabAddEvent);
    }

    @Override
    public Observable<String> onSearchTextChanged() {
        return searchTextSubject;
    }

    @Override
    public Observable<Object> onSwipeRefresh() {
        return swipeRefresh;
    }

    @Override
    public Observable<LocationModel> onSuggestionListener() {
        return onSuggestionClick
                .doOnNext(locationModel -> {
                    latitude = locationModel.getLat();
                    longitude = locationModel.getLon();
                });
    }

    @Override
    public Observable<Object> onShowDataFromDb() {
        return onShowDataFromDbClick;
    }

    @Override
    public void updateGroupCards(List<SingleGroupModel> data) {
        groupAdapter.setGroupList(data);
    }

    @Override
    public void showAlertWithChoice(String message) {
        if (dialog != null && dialog.isShowing()) {
            dialog.setMessage(message);
            return;
        }
        dialog = new AlertDialog.Builder(this)
                .setMessage(message)
                .setCancelable(false)
                .setNegativeButton(R.string.see_stored_data, (dialog, which) -> {
                    dialog.dismiss();
                    onShowDataFromDbClick.onNext(new Object());
                })
                .setPositiveButton(R.string.no, (dialog, which) -> dialog.dismiss()).show();
    }

    @Override
    public void showAlert(String message) {
        if (dialog != null && dialog.isShowing()) {
            dialog.setMessage(message);
            return;
        }
        dialog = new AlertDialog.Builder(this)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss()).show();
    }

    @Override
    public void showMessage(int message) {
        showAlert(getString(message));
    }

    @Override
    public void showLoading() {
        loaderContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loaderContainer.setVisibility(View.GONE);
    }

    @Override
    public void fillSearchList(List<LocationModel> locations) {
        String[] columns = {BaseColumns._ID,
                SearchManager.SUGGEST_COLUMN_TEXT_1,
                SearchManager.SUGGEST_COLUMN_INTENT_DATA};
        MatrixCursor cursor = new MatrixCursor(columns);
        this.locations.clear();
        this.locations.addAll(locations);
        for (int i = 0; i < locations.size(); i++) {
            String[] itemSuggestion = {Integer.toString(i), locations.get(i).getNameString(), locations.get(i).getNameString()};
            cursor.addRow(itemSuggestion);
        }
        suggestionAdapter.swapCursor(cursor);
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }
}


