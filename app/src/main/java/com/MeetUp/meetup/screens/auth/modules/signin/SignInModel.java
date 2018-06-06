package com.MeetUp.meetup.screens.auth.modules.signin;

import com.MeetUp.meetup.data.assets.User;
import com.MeetUp.meetup.screens.auth.modules.signin.server.SignInMockServer;

import io.reactivex.Observable;

public class SignInModel {

    private final SignInMockServer server;

    public SignInModel(SignInMockServer server) {
        this.server = server;
    }

    public Observable<User> doSignIn(String email, String password) {
        return Observable.just(server.signIn(email, password));
    }
}
