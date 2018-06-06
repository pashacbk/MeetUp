package com.MeetUp.meetup.screens.auth.modules.signin.server;

import com.MeetUp.meetup.data.assets.AssetsHelper;
import com.MeetUp.meetup.data.assets.User;

import java.util.ArrayList;
import java.util.List;

public class SignInMockServer {

    private AssetsHelper helper;
    private String path;
    private List<User> users = new ArrayList<>();
    private static SignInMockServer server;

    public static SignInMockServer getInstance(AssetsHelper helper, String path) {
        if (server == null) {
            server = new SignInMockServer(helper, path);
        }
        return server;
    }

    private SignInMockServer(AssetsHelper helper, String path) {
        this.helper = helper;
        this.path = path;
        init();
    }

    public User signIn(String email, String password) {
        User currentUser = null;
        for (User user : users) {
            if (email.equals(user.getEmail()) && password.equals(user.getPassword())) {
                currentUser = user;
                break;
            }
            else {
                currentUser = new User();
                break;
            }
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return currentUser;
    }

    private void init() {
        users.addAll(helper.getUsers(path));
    }

}
