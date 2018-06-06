package com.MeetUp.meetup.data.assets;

import android.content.res.AssetManager;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class AssetsHelper {

    private static final String EMPTY_STRING = "";
    private static final String TAG = AssetManager.class.getSimpleName();
    private final AssetManager manager;
    private final UserParser parser;

    public AssetsHelper(AssetManager manager, UserParser parser) {
        this.manager = manager;
        this.parser = parser;
    }

    public List<User> getUsers(String path) {
        return parser.parseStringToObject(loadStringFromAsset(path));
    }

    private String loadStringFromAsset(String path) {
        String json = EMPTY_STRING;
        try {
            InputStream is = getStreamFromAssets(new File(path));
            byte[] buffer = new byte[1024];
            StringBuilder stringBuilder = new StringBuilder();
            while (is.read(buffer) != -1) {
                stringBuilder.append(new String(buffer, "UTF-8"));
            }
            is.close();
            json = stringBuilder.toString().trim();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return json;
    }

    private InputStream getStreamFromAssets(File file) throws IOException {
        return manager.open(file.getPath());
    }
}
