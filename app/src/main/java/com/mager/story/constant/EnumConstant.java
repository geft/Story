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
    public @interface DialogStyle {
        String FULL_SCREEN = "FULL_SCREEN";
    }

    @StringDef
    @Retention(RetentionPolicy.SOURCE)
    public @interface SnackBarType {
        String NORMAL = "NORMAL";
        String ERROR = "ERROR";
    }

    @StringDef
    @Retention(RetentionPolicy.SOURCE)
    public @interface FolderType {
        String STORY = "story";
        String PHOTO = "photo";
        String AUDIO = "audio";
        String VIDEO = "video";
        String MENU = "menu";
    }

    @StringDef
    @Retention(RetentionPolicy.SOURCE)
    public @interface PhotoType {
        String THUMB = "thumb";
        String FULL = "full";
    }

    @StringDef
    @Retention(RetentionPolicy.SOURCE)
    public @interface DownloadType {
        String MENU_JSON = "MENU_JSON";
        String MENU_PHOTO = "MENU_PHOTO";
        String MENU_STORY = "MENU_STORY";
        String PHOTO = "PHOTO";
        String STORY = "STORY";
        String AUDIO = "AUDIO";
        String VIDEO = "VIDEO";
    }

    @StringDef
    @Retention(RetentionPolicy.SOURCE)
    public @interface FilePrefix {
        String PHOTO = "album_";
        String STORY = "book_";
    }

    @StringDef
    @Retention(RetentionPolicy.SOURCE)
    public @interface FileExtension {
        String PHOTO = ".jpg";
        String STORY = ".jpg";
    }
}
