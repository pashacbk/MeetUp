package com.MeetUp.meetup.utils.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.MeetUp.meetup.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragment extends Fragment implements BaseView {

    private Unbinder unbinder;

    protected void replaceFragment(@IdRes int container,
                                   Fragment fragment,
                                   String TAG,
                                   boolean shouldAddToBackStack) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        setupFragmentComponent();
    }

    protected abstract void setupFragmentComponent();
    @Override
    public void onDestroyView() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroyView();
    }
}
