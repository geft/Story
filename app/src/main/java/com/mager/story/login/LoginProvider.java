package com.mager.story.login;

import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.mager.story.StoryApplication;
import com.mager.story.datamodel.MenuDataModel;

/**
 * Created by Gerry on 29/10/2016.
 */

class LoginProvider {

    private static final String EMAIL = "EMAIL";
    private static final String MENU_JSON = "MENU_JSON";

    @Nullable
    private MenuDataModel menuDataModel;

    public LoginProvider() {
        menuDataModel = loadMenuDataFromPrefs();
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
    private MenuDataModel loadMenuDataFromPrefs() {
        String json = StoryApplication.getSharedPreferences().getString(MENU_JSON, null);
        if (json == null) return null;

        return new Gson().fromJson(json, MenuDataModel.class);
    }

    void clearData() {
        StoryApplication.getSharedPreferences().edit()
                .remove(MENU_JSON)
                .remove(EMAIL)
                .apply();
    }

    void saveMenuData(MenuDataModel dataModel) {
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
}
