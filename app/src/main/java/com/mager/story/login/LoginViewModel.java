package com.mager.story.login;

import android.databinding.Bindable;

import com.mager.story.BR;
import com.mager.story.core.CoreViewModel;

import org.parceler.Parcel;

/**
 * Created by Gerry on 24/09/2016.
 */

@Parcel
public class LoginViewModel extends CoreViewModel {

    protected boolean loading;
    protected String email;
    protected String password;

    @Bindable
    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
        notifyPropertyChanged(BR.loading);
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
}
