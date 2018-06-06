package com.MeetUp.meetup.screens.auth.modules.signin.server;

import android.os.AsyncTask;

import com.MeetUp.meetup.data.assets.User;
import com.MeetUp.meetup.utils.AsyncTaskCallback;

public class SignInAsyncTask extends AsyncTask<String, Void, User> {

    private final SignInMockServer server;
    private AsyncTaskCallback<User> callback;

    public SignInAsyncTask(SignInMockServer server, AsyncTaskCallback<User> callback) {
        this.server = server;
        this.callback = callback;
    }

    @Override
    protected User doInBackground(String... strings) {
        return server.signIn(strings[0], strings[1]);
    }

    @Override
    protected void onPostExecute(User data) {
            callback.doInUiThread(data);
    }
}
