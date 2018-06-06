package com.MeetUp.meetup.screens.splash;

import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import com.MeetUp.meetup.MeetUpApp;
import com.MeetUp.meetup.R;
import com.MeetUp.meetup.screens.auth.AuthActivity;
import com.MeetUp.meetup.utils.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;

public class SplashActivity extends BaseActivity implements SplashView {

    public static final int SPLASH_TEXT_TIME_ANIMATION = 3 * 1000;

    @Inject
    SplashPresenter presenter;
    @BindView(R.id.app_name)
    TextView appName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(SPLASH_TEXT_TIME_ANIMATION);
        appName.setAnimation(animation);
        animation.start();
        presenter.attachView(this);
    }

    @Override
    protected int getView() {
        return R.layout.activity_splash;
    }

    @Override
    public void setupActivityComponent() {
        MeetUpApp.getAppComponent()
                .splashActivityBuilder()
                .build()
                .inject(this);
    }

    @Override
    public void startIntroActivity() {
        AuthActivity.newIntent(this);
        finish();
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }
}
