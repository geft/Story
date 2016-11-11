package com.mager.story.login;

import android.support.annotation.ArrayRes;

import com.mager.story.R;
import com.mager.story.StoryApplication;
import com.mager.story.constant.EnumConstant.FolderType;
import com.mager.story.core.CorePresenter;
import com.mager.story.data.MenuData;
import com.mager.story.util.EncryptionUtil;
import com.mager.story.util.FileUtil;
import com.mager.story.util.ResourceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gerry on 23/10/2016.
 */

class LoginPresenter extends CorePresenter<LoginViewModel> {

    private LoginProvider provider;

    LoginPresenter(LoginViewModel viewModel) {
        super(viewModel);

        provider = new LoginProvider();
    }

    void loadEmail() {
        getViewModel().setEmail(provider.loadEmail());
    }

    public void setLoading(boolean loading) {
        getViewModel().setLoading(loading);
    }

    void setMenuDataModel(MenuData dataModel) {
        getViewModel().setMenuData(dataModel);
    }

    void saveMenuDataToDevice() {
        provider.saveMenuData(getViewModel().getMenuData());
    }

    public void incrementAriesCount() {
        getViewModel().setAriesCount(getViewModel().getAriesCount() + 1);
    }

    public void saveEmailToDevice() {
        provider.saveEmail(getViewModel().getEmail());
    }

    public void clearOutdatedData() {
        List<String> removeList = new ArrayList<>();

        removeFolderContentIfOutdated(removeList, FolderType.MENU);
        removeFolderContentIfOutdated(removeList, FolderType.PHOTO);
        removeFolderContentIfOutdated(removeList, FolderType.STORY);
        removeFolderContentIfOutdated(removeList, FolderType.AUDIO);
        removeFolderContentIfOutdated(removeList, FolderType.VIDEO);

        showRemovalToast(removeList);
    }

    private void showRemovalToast(List<String> removeList) {
        StringBuilder builder = new StringBuilder(ResourceUtil.getString(R.string.login_folder_removed));

        for (int i = 0; i < removeList.size(); i++) {
            String item = removeList.get(i);
            builder.append(item);

            if (i != removeList.size() - 1) {
                builder.append(", ");
            }
        }

        if (!removeList.isEmpty()) {
            ResourceUtil.showToast(builder.toString());
        }
    }

    private void removeFolderContentIfOutdated(List<String> removeList, @FolderType String folderType) {
        MenuData localData = provider.getLocalData();
        MenuData currentData = getViewModel().getMenuData();

        if (!provider.isLocalDataValid(localData, currentData, folderType)) {
            FileUtil.removeFolderContent(folderType);

            removeList.add(folderType);
        }
    }

    public boolean isValidOnline() {
        return getViewModel().getAriesCount() == StoryApplication.ARIES_COUNT;
    }

    public boolean isValidOffline() {
        return isOfflineAriesCount() && isValidEmail() && isValidPassword();
    }

    private boolean isValidEmail() {
        return isValidKey(R.array.valid_email, getViewModel().getEmail());
    }

    private boolean isValidPassword() {
        return isValidKey(R.array.valid_password, getViewModel().getPassword());
    }

    private boolean isValidKey(@ArrayRes int arrayRes, String key) {
        for (String password : ResourceUtil.getStringArray(arrayRes)) {
            String encryptedValue = EncryptionUtil.encrypt(key);
            if (encryptedValue != null && encryptedValue.equalsIgnoreCase(password)) {
                return true;
            }
        }

        return false;
    }

    private boolean isOfflineAriesCount() {
        return getViewModel().getAriesCount() == StoryApplication.ARIES_COUNT_OFFLINE;
    }

    public boolean doesMenuDataExist() {
        return provider.getLocalData() != null;
    }

    public void setLocalMenuData() {
        getViewModel().setMenuData(provider.getLocalData());
    }

    public void incrementWrongCount() {
        getViewModel().wrongCount.set(getViewModel().wrongCount.get() + 1);
    }
}
