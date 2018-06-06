package com.MeetUp.meetup.screens.auth.modules.signin.server;

import com.MeetUp.meetup.data.assets.User;

public interface SignInListener {

    void onSuccess(User user);
    void onError(int errorCode);

}
