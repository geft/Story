package com.mager.story.constant;

import android.Manifest;
import android.support.annotation.IntDef;
import android.support.annotation.StringDef;
import android.util.SparseIntArray;

import com.mager.story.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Gerry on 08/10/2016.
 */

public class EnumConstant {

    public static SparseIntArray getMenuArray() {
        SparseIntArray layoutArray = new SparseIntArray();
        layoutArray.put(0, R.layout.menu_photo);
        layoutArray.put(1, R.layout.menu_story);
        layoutArray.put(2, R.layout.menu_audio);

        return layoutArray;
    }

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
}
