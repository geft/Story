package com.mager.story.home;

import android.databinding.Bindable;

import com.mager.story.BR;
import com.mager.story.core.CoreViewModel;
import com.mager.story.data.MenuData;

import org.parceler.Parcel;

/**
 * Created by Gerry on 24/10/2016.
 */

@Parcel
public class HomeViewModel extends CoreViewModel {

    protected boolean loading;
    protected String selectedItem;
    protected MenuData menuData;

    @Bindable
    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
        notifyPropertyChanged(BR.loading);
    }

    public String getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(String selectedItem) {
        this.selectedItem = selectedItem;
    }

    public MenuData getMenuData() {
        return menuData;
    }

    public void setMenuData(MenuData menuData) {
        this.menuData = menuData;
    }
}
