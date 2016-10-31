package com.mager.story;

import android.app.Application;
import android.content.SharedPreferences;

import com.google.firebase.FirebaseApp;

/**
 * Created by Gerry on 23/09/2016.
 */

public class StoryApplication extends Application {

    public static final int ARIES_COUNT = 2;
    private static final String PREFS_NAME = "PREFS";

    private static StoryApplication instance;
    private static SharedPreferences sharedPreferences;

    public static StoryApplication getInstance() {
        return instance;
    }

    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        initFirebase();
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(this);
    }
}
