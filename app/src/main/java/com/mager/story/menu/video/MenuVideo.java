package com.mager.story.menu.video;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.mager.story.BR;

import org.parceler.Parcel;

/**
 * Created by Gerry on 29/10/2016.
 */

@Parcel
public class MenuVideo extends BaseObservable {
    protected boolean offline;
    protected String name;
    protected String code;

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public boolean isOffline() {
        return offline;
    }

    public void setOffline(boolean offline) {
        this.offline = offline;
        notifyPropertyChanged(BR.offline);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
