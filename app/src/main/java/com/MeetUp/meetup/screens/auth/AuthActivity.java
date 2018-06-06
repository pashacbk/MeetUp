package com.MeetUp.meetup.screens.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.MeetUp.meetup.MeetUpApp;
import com.MeetUp.meetup.R;
import com.MeetUp.meetup.screens.auth.modules.intro.IntroFragment;
import com.MeetUp.meetup.utils.base.BaseActivity;

import javax.inject.Inject;

public class AuthActivity extends BaseActivity implements AuthActivityView {

    @Inject
    AuthActivityPresenter presenter;

    public static void newIntent(Context context) {
        context.startActivity(new Intent(context, AuthActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.attachView(this);
    }

    @Override
    protected int getView() {
        return R.layout.activity_auth;
    }

    @Override
    public void setupActivityComponent() {
        MeetUpApp.getAppComponent()
                .authActivityBuilder()
                .build()
                .inject(this);
    }

    @Override
    public void showFragment() {
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.container);
        if (fragment == null) {
            replaceFragment(R.id.container, IntroFragment.newInstance(), IntroFragment.TAG, false);
        }
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }
}
