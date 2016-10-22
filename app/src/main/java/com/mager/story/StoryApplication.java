package com.mager.story;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Gerry on 23/09/2016.
 */

public class StoryApplication extends Application {

    private static StoryApplication instance;

    public static StoryApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        initFirebase();
        initLeakCanary();
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(this);
    }

    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }
}
