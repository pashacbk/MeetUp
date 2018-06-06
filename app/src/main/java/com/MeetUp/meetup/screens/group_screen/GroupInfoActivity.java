package com.MeetUp.meetup.screens.group_screen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.MeetUp.meetup.MeetUpApp;
import com.MeetUp.meetup.R;
import com.MeetUp.meetup.screens.group_screen.dagger.GroupInfoActivityModule;
import com.MeetUp.meetup.utils.base.BaseActivity;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;

public class GroupInfoActivity extends BaseActivity implements GroupInfoActivityView {

    public static final int DEFAULT_VALUE = 0;
    public static String GROUP_ID = "GROUP_ID";
    private AlertDialog dialog;

    @BindView(R.id.toolbar_group_info)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.collapsing_image)
    ImageView groupImage;
    @BindView(R.id.group_members)
    TextView members;
    @BindView(R.id.group_description)
    TextView description;
    @BindView(R.id.group_status)
    TextView status;

    @Inject
    GroupInfoActivityPresenter presenter;

    public static Intent newIntent(Context context, int groupId) {
        Intent intent = new Intent(context, GroupInfoActivity.class);
        return intent.putExtra(GROUP_ID, groupId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar();
        presenter.attachView(this);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public int getGroupId() {
        return getIntent().getIntExtra(GROUP_ID, DEFAULT_VALUE);
    }

    @Override
    public void setCollapsingToolbarTitle(CharSequence title) {
        collapsingToolbarLayout.setTitle(title);
    }

    @Override
    public void setGroupImageFromNetwork(String photoLink) {
        Picasso.get().load(photoLink).into(groupImage);
    }

    @Override
    public void setGroupImageDefault() {
        groupImage.setImageResource(R.drawable.ic_group_no_key_photo);
    }

    @Override
    public void setGroupMembers(CharSequence groupMembers) {
        members.setText(groupMembers);
    }

    @Override
    public void setGroupDescription(String groupDescription) {
        description.setText(Html.fromHtml(groupDescription));
    }

    @Override
    public void setGroupStatus(CharSequence groupStatus) {
        status.setText(groupStatus);
    }

    @Override
    protected int getView() {
        return R.layout.activity_group_info;
    }

    @Override
    public void showAlert(CharSequence message) {
        if (dialog != null && dialog.isShowing()) {
            dialog.setMessage(message);
            return;
        }
        dialog = new AlertDialog.Builder(this)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    public void showError(int errorCode) {
        showAlert(getString(errorCode));
    }

    @Override
    protected void setupActivityComponent() {
        MeetUpApp.getAppComponent()
                .groupInfoActivityBuilder()
                .groupInfoActivityModule(new GroupInfoActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }
}
