package com.mager.story.login;

import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.mager.story.StoryApplication;
import com.mager.story.datamodel.MenuDataModel;

/**
 * Created by Gerry on 29/10/2016.
 */

class LoginProvider {

    private static final String MENU_JSON = "MENU_JSON";

    @Nullable
    private MenuDataModel menuDataModel;

    public LoginProvider() {
        menuDataModel = getMenuDataFromPrefs();
    }

    @Nullable
    public MenuDataModel getMenuDataModel() {
        return menuDataModel;
    }

    boolean isLatestMenu(int version) {
        return menuDataModel != null && version == menuDataModel.version;
    }

    boolean doesMenuDataExistOnDevice() {
        return menuDataModel != null;
    }

    @Nullable
    private MenuDataModel getMenuDataFromPrefs() {
        String json = StoryApplication.getSharedPreferences().getString(MENU_JSON, null);
        if (json == null) return null;

        return new Gson().fromJson(json, MenuDataModel.class);
    }

    void clearMenuData() {
        StoryApplication.getSharedPreferences().edit()
                .remove(MENU_JSON)
                .apply();
    }

    void saveMenuData(MenuDataModel dataModel) {
        StoryApplication.getSharedPreferences().edit()
                .putString(MENU_JSON, new Gson().toJson(dataModel))
                .apply();
    }
}
