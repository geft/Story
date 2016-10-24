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
    public @interface SnackBarType {
        String NORMAL = "NORMAL";
        String ERROR = "ERROR";
    }

    @StringDef
    @Retention(RetentionPolicy.SOURCE)
    public @interface MenuType {
        String STORY = "story";
        String PHOTO = "photo";
        String AUDIO = "audio";
    }

    @StringDef
    @Retention(RetentionPolicy.SOURCE)
    public @interface PhotoType {
        String THUMB = "thumb";
        String FULL = "full";
    }

    @StringDef
    @Retention(RetentionPolicy.SOURCE)
    public @interface PhotoGroup {
        String ONS = "ons";
        String AIRY = "airy";
        String SWISSBEL = "swissbel";
    }

    @StringDef
    @Retention(RetentionPolicy.SOURCE)
    public @interface StoryChapter {
        String CH0 = "Prologue";
        String CH1 = "Chapter 1";
        String CH2 = "Chapter 2";
        String CH3 = "Chapter 3";
        String CH4 = "Chapter 4";
    }

    @StringDef
    @Retention(RetentionPolicy.SOURCE)
    public @interface StoryTitle {
        String CH0 = "First Contact";
        String CH1 = "First Date";
        String CH2 = "First Kiss";
        String CH3 = "First Foreplay";
        String CH4 = "First Orgasm";
    }
}
