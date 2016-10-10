package com.mager.story.constant;

import android.Manifest;
import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Gerry on 08/10/2016.
 */

public class EnumConstant {

    @IntDef
    @Retention(RetentionPolicy.SOURCE)
    public @interface RequestCode {
        int PERMISSION = 100;
    }

    @StringDef
    @Retention(RetentionPolicy.SOURCE)
    public @interface PermissionType {
        String PHONE = Manifest.permission.READ_PHONE_STATE;
    }

    @StringDef
    @Retention(RetentionPolicy.SOURCE)
    public @interface FolderType {
        String STORY = "story";
        String PHOTO = "photo";
        String AUDIO = "audio";
    }
}
