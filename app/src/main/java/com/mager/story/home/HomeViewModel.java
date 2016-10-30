package com.mager.story.home;

import android.databinding.Bindable;

import com.mager.story.BR;
import com.mager.story.core.CoreViewModel;
import com.mager.story.datamodel.MenuDataModel;

import org.parceler.Parcel;

/**
 * Created by Gerry on 24/10/2016.
 */

@Parcel
public class HomeViewModel extends CoreViewModel {

    protected boolean loading;
    protected boolean showBottomView;
    protected boolean isLoggedIn;
    protected String selectedItem;
    protected MenuDataModel menuDataModel;

    @Bindable
    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
        notifyPropertyChanged(BR.loading);
    }

    @Bindable
    public boolean isShowBottomView() {
        return showBottomView;
    }

    public void setShowBottomView(boolean showBottomView) {
        this.showBottomView = showBottomView;
        notifyPropertyChanged(BR.showBottomView);
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public String getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(String selectedItem) {
        this.selectedItem = selectedItem;
    }

    public MenuDataModel getMenuDataModel() {
        return menuDataModel;
    }

    public void setMenuDataModel(MenuDataModel menuDataModel) {
        this.menuDataModel = menuDataModel;
    }
}
