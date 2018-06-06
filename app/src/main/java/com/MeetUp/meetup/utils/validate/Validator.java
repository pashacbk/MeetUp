package com.MeetUp.meetup.utils.validate;

public interface Validator<T> {
    boolean isValid(T data);
}
