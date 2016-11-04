package com.mager.story.login;

import com.mager.story.constant.EnumConstant.FolderType;
import com.mager.story.core.CorePresenter;
import com.mager.story.datamodel.MenuDataModel;
import com.mager.story.util.FileUtil;

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

    void clearMenuData() {
        provider.clearData();
    }

    boolean isMenuDataOnDeviceValid(MenuDataModel dataModel) {
        return provider.doesMenuDataExistOnDevice() && provider.isLatestMenu(dataModel.version);
    }

    void setMenuDataModel(MenuDataModel dataModel) {
        getViewModel().setMenuDataModel(dataModel);
    }

    void saveMenuDataToDevice() {
        provider.saveMenuData(getViewModel().getMenuDataModel());
    }

    public void incrementAriesCount() {
        getViewModel().setAriesCount(getViewModel().getAriesCount() + 1);
    }

    public void saveEmailToDevice() {
        provider.saveEmail(getViewModel().getEmail());
    }

    public void clearOutdatedData() {
        removeFolderContentIfOutdated(FolderType.PHOTO);
        removeFolderContentIfOutdated(FolderType.STORY);
        removeFolderContentIfOutdated(FolderType.AUDIO);
        removeFolderContentIfOutdated(FolderType.VIDEO);
    }

    private void removeFolderContentIfOutdated(@FolderType String folderType) {
        MenuDataModel localData = provider.getLocalData();
        MenuDataModel currentData = getViewModel().getMenuDataModel();

        if (!provider.isLocalDataValid(localData, currentData, folderType)) {
            FileUtil.removeFolderContent(folderType);
        }
    }
}
