package com.MeetUp.meetup.utils;

public interface AsyncTaskCallback<T> {
    void doInUiThread(T t);
}
