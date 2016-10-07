package com.mager.story.util;

import android.Manifest;
import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Gerry on 08/10/2016.
 */

public class StoryConstant {

    @IntDef
    @Retention(RetentionPolicy.SOURCE)
    public @interface RequestCode {
        int PERMISSION = 100;
        int FIREBASE_SIGN_IN = 101;
    }

    @StringDef
    @Retention(RetentionPolicy.SOURCE)
    public @interface PermissionType {
        String PHONE = Manifest.permission.READ_PHONE_STATE;
    }
}
