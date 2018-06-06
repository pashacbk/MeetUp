package com.MeetUp.meetup.utils;

import android.text.Editable;
import android.text.TextWatcher;

public abstract class TextChangedListener implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //do nothing
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //do nothing
    }

    @Override
    public void afterTextChanged(Editable s) {}

}
