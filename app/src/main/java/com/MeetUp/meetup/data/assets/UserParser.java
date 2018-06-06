package com.MeetUp.meetup.data.assets;


import android.util.Log;

import com.MeetUp.meetup.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserParser implements JSONParser<List<User>> {

    private static final String TAG = UserParser.class.getSimpleName();

    @Override
    public List<User> parseStringToObject(String jsonString) {
        List<User> users = new ArrayList<>();
        if (StringUtils.isNotEmpty(jsonString)) {
            try {
                Gson gson = new Gson();
                Type userListType = new TypeToken<List<User>>() {
                }.getType();
                users = gson.fromJson(jsonString, userListType);
            } catch (JsonParseException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }
        return users;
    }

}
