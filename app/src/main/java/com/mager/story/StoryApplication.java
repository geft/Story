package com.mager.story;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Gerry on 23/09/2016.
 */

public class StoryApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initLeakCanary();
    }

    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }
}
