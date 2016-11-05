package com.mager.story;

import android.app.Application;
import android.content.SharedPreferences;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.google.firebase.FirebaseApp;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Gerry on 23/09/2016.
 */

public class StoryApplication extends Application {

    public static final int ARIES_COUNT = 2;
    public static final int ARIES_COUNT_OFFLINE = 5;
    private static final String PREFS_NAME = "PREFS";
    private static boolean offline;
    private static StoryApplication instance;
    private static SharedPreferences sharedPreferences;

    public static StoryApplication getInstance() {
        return instance;
    }

    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public static boolean isOffline() {
        return offline;
    }

    public static void setOffline(boolean offline) {
        StoryApplication.offline = offline;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initFabric();
        instance = this;
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        initFirebase();
    }

    private void initFabric() {
        Crashlytics crashlyticsKit = new Crashlytics.Builder()
                .core(new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
                .build();

        Fabric.with(this, crashlyticsKit);
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(this);
    }
}
