package com.mager.story.util

import android.util.Log

import com.crashlytics.android.Crashlytics

/**
 * Created by Gerry on 01/11/2016.
 */

object LogUtil {

    fun logError(throwable: Throwable) {
        Crashlytics.logException(throwable)
    }

    fun logError(TAG: String, message: String, throwable: Throwable) {
        Crashlytics.log(Log.ERROR, TAG, message)
        logError(throwable)
    }

    fun logWarning(tag: String, message: String) {
        Crashlytics.log(Log.WARN, tag, message)
    }

    fun logInfo(tag: String, message: String) {
        Crashlytics.log(Log.INFO, tag, message)
    }

    fun logDebug(tag: String, message: String) {
        Crashlytics.log(Log.DEBUG, tag, message)
    }
}
