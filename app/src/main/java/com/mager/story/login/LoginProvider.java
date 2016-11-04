package com.mager.story.login;

import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.mager.story.StoryApplication;
import com.mager.story.constant.EnumConstant.FolderType;
import com.mager.story.data.MenuData;

/**
 * Created by Gerry on 29/10/2016.
 */

class LoginProvider {

    private static final String EMAIL = "EMAIL";
    private static final String MENU_JSON = "MENU_JSON";

    private MenuData localData;

    public LoginProvider() {
        this.localData = loadLocalData();
    }

    public MenuData getLocalData() {
        return localData;
    }

    boolean isLatestMenu(int version) {
        MenuData localData = loadLocalData();
        return localData != null && version == localData.version;
    }

    boolean doesMenuDataExistOnDevice() {
        return loadLocalData() != null;
    }

    @Nullable
    private MenuData loadLocalData() {
        String json = StoryApplication.getSharedPreferences().getString(MENU_JSON, null);
        if (json == null) return null;

        return new Gson().fromJson(json, MenuData.class);
    }

    void clearData() {
        StoryApplication.getSharedPreferences().edit()
                .remove(MENU_JSON)
                .remove(EMAIL)
                .apply();
    }

    void saveMenuData(MenuData dataModel) {
        StoryApplication.getSharedPreferences().edit()
                .putString(MENU_JSON, new Gson().toJson(dataModel))
                .apply();
    }

    void saveEmail(String email) {
        StoryApplication.getSharedPreferences().edit()
                .putString(EMAIL, email)
                .apply();
    }

    @Nullable
    String loadEmail() {
        return StoryApplication.getSharedPreferences().getString(EMAIL, null);
    }

    boolean isLocalDataValid(MenuData localData, MenuData currentData, @FolderType String folderType) {
        switch (folderType) {
            case FolderType.PHOTO:
                return isVersionValid(localData.versionPhoto, currentData.versionPhoto);
            case FolderType.STORY:
                return isVersionValid(localData.versionStory, currentData.versionStory);
            case FolderType.AUDIO:
                return isVersionValid(localData.versionAudio, currentData.versionAudio);
            case FolderType.VIDEO:
                return isVersionValid(localData.versionVideo, currentData.versionVideo);
            default:
                return false;
        }
    }

    private boolean isVersionValid(int versionLocal, int versionCurrent) {
        return versionCurrent == versionLocal;
    }
}
