package com.mager.story.constant;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Gerry on 08/10/2016.
 */

public class EnumConstant {

    @StringDef
    @Retention(RetentionPolicy.SOURCE)
    public @interface Tag {
        String LOGIN = "LOGIN";
        String MENU = "MENU";
        String PHOTO = "MENU_PHOTO";
        String STORY = "MENU_STORY";
        String AUDIO = "AUDIO";
        String VIDEO = "VIDEO";
        String FILE = "FILE";
    }

    @IntDef
    @Retention(RetentionPolicy.SOURCE)
    public @interface RequestCode {
        int PERMISSION = 100;
    }

    @StringDef
    @Retention(RetentionPolicy.SOURCE)
    public @interface DialogStyle {
        String NORMAL = "NORMAL";
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
        String JSON = "json";
        String STORY = "story";
        String PHOTO = "photo";
        String AUDIO = "audio";
        String VIDEO = "video";
        String MENU = "menu";
    }

    @StringDef
    @Retention(RetentionPolicy.SOURCE)
    public @interface DownloadType {
        String MENU_JSON = "MENU_JSON";
        String MENU_PHOTO = "MENU_PHOTO";
        String MENU_STORY = "MENU_STORY";
        String PHOTO_FULL = "PHOTO_FULL";
        String PHOTO_THUMB = "PHOTO_THUMB";
        String STORY = "STORY";
        String AUDIO = "AUDIO";
        String VIDEO = "VIDEO";
    }

    @StringDef
    @Retention(RetentionPolicy.SOURCE)
    public @interface FilePrefix {
        String MENU_PHOTO = "album_";
        String MENU_STORY = "book_";
        String PHOTO_THUMB = "thumb";
        String PHOTO_FULL = "full";
    }

    @StringDef
    @Retention(RetentionPolicy.SOURCE)
    public @interface FileExtension {
        String JSON = ".json";
        String MENU_PHOTO = ".jpg";
        String MENU_STORY = ".jpg";
        String PHOTO = ".jpg";
        String STORY = ".txt";
        String AUDIO = ".amr";
        String VIDEO = ".webm";
    }
}
