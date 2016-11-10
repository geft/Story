package com.mager.story.login;

import android.databinding.Bindable;
import android.databinding.ObservableInt;

import com.mager.story.BR;
import com.mager.story.BuildConfig;
import com.mager.story.core.CoreViewModel;
import com.mager.story.data.MenuData;

import org.parceler.Parcel;

/**
 * Created by Gerry on 24/09/2016.
 */

@Parcel
public class LoginViewModel extends CoreViewModel {

    boolean loading;
    int ariesCount;
    ObservableInt wrongCount = new ObservableInt();

    String email;
    String password;
    String version = BuildConfig.VERSION_NAME;

    MenuData menuData;

    @Bindable
    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
        notifyPropertyChanged(BR.loading);
    }

    public int getAriesCount() {
        return ariesCount;
    }

    public void setAriesCount(int ariesCount) {
        this.ariesCount = ariesCount;
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    public MenuData getMenuData() {
        return menuData;
    }

    public void setMenuData(MenuData menuData) {
        this.menuData = menuData;
    }

    public String getVersion() {
        return version;
    }
}
