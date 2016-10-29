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
    protected boolean loadingProgress;
    protected float progressValue;
    protected boolean showBottomView;
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
    public boolean isLoadingProgress() {
        return loadingProgress;
    }

    public void setLoadingProgress(boolean loadingProgress) {
        this.loadingProgress = loadingProgress;
        notifyPropertyChanged(BR.loadingProgress);
    }

    @Bindable
    public float getProgressValue() {
        return progressValue;
    }

    public void setProgressValue(float progressValue) {
        this.progressValue = progressValue;
        notifyPropertyChanged(BR.progressValue);
    }

    @Bindable
    public boolean isShowBottomView() {
        return showBottomView;
    }

    public void setShowBottomView(boolean showBottomView) {
        this.showBottomView = showBottomView;
        notifyPropertyChanged(BR.showBottomView);
    }

    public MenuDataModel getMenuDataModel() {
        return menuDataModel;
    }

    public void setMenuDataModel(MenuDataModel menuDataModel) {
        this.menuDataModel = menuDataModel;
    }
}
