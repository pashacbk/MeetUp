package com.MeetUp.meetup.utils;

public interface ApiResultCallback<T> {

    void onSuccess(T t);
    void onError(String errorMessage);
}
