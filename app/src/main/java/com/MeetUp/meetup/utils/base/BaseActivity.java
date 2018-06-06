package com.MeetUp.meetup.utils.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.MeetUp.meetup.R;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    protected void replaceFragment(@IdRes int container,
                                   Fragment fragment,
                                   String TAG,
                                   boolean shouldAddToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in,
                R.anim.slide_out,
                R.anim.back_in,
                R.anim.back_out);
        transaction.replace(container, fragment, TAG);
        if (shouldAddToBackStack) {
            transaction.addToBackStack(TAG);
        }
        transaction.commit();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getView());
        ButterKnife.bind(this);
        setupActivityComponent();
    }

    protected abstract @LayoutRes int getView();

    protected abstract void setupActivityComponent();
}
