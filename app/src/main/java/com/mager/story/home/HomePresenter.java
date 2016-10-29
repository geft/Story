package com.mager.story.home;

import com.mager.story.core.CorePresenter;
import com.mager.story.datamodel.MenuDataModel;

/**
 * Created by Gerry on 24/10/2016.
 */

class HomePresenter extends CorePresenter<HomeViewModel> {

    private HomeProvider provider;

    HomePresenter(HomeViewModel viewModel) {
        super(viewModel);

        provider = new HomeProvider();
    }

    void setLoading(boolean loading) {
        getViewModel().setLoading(loading);
    }

    void setShowBottomView(boolean show) {
        getViewModel().setShowBottomView(show);
    }

    void clearMenuData() {
        provider.clearMenuData();
    }

    boolean isMenuDataOnDeviceValid(MenuDataModel dataModel) {
        return provider.doesMenuDataExistOnDevice() && provider.isLatestMenu(dataModel.version);
    }

    void setMenuDataModel(MenuDataModel dataModel) {
        getViewModel().setMenuDataModel(dataModel);
    }

    public void saveMenuDataToDevice() {
        provider.saveMenuData(getViewModel().getMenuDataModel());
    }
}
