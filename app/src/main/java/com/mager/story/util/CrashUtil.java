package com.mager.story.util;

import android.util.Log;

import com.crashlytics.android.Crashlytics;

/**
 * Created by Gerry on 01/11/2016.
 */

public class CrashUtil {

    public static void logError(Throwable throwable) {
        Crashlytics.logException(throwable);
    }

    public static void logError(String TAG, String message, Throwable throwable) {
        Crashlytics.log(Log.ERROR, TAG, message);
        logError(throwable);
    }

    public static void logWarning(String tag, String message) {
        Crashlytics.log(Log.WARN, tag, message);
    }

    public static void logInfo(String tag, String message) {
        Crashlytics.log(Log.INFO, tag, message);
    }
}
