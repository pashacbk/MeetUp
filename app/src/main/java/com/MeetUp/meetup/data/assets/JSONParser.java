package com.MeetUp.meetup.data.assets;

public interface JSONParser<T> {
    T parseStringToObject(String jsonString);
}
