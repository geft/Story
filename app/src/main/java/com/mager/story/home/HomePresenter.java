package com.mager.story.home;

import com.mager.story.core.CorePresenter;
import com.mager.story.data.MenuData;

/**
 * Created by Gerry on 24/10/2016.
 */

class HomePresenter extends CorePresenter<HomeViewModel> {

    HomePresenter(HomeViewModel viewModel) {
        super(viewModel);
    }

    void setLoading(boolean loading) {
        getViewModel().setLoading(loading);
    }

    void setSelectedItem(String selectedItem) {
        getViewModel().setSelectedItem(selectedItem);
    }

    void setMenuDataModel(MenuData menuData) {
        getViewModel().setMenuData(menuData);
    }
}
