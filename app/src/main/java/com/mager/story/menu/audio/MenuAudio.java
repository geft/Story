package com.mager.story.menu.audio;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableBoolean;

import com.mager.story.BR;

import org.parceler.Parcel;

/**
 * Created by Gerry on 21/10/2016.
 */

@Parcel
public class MenuAudio extends BaseObservable {
    public ObservableBoolean offline = new ObservableBoolean();
    public ObservableBoolean loading = new ObservableBoolean();
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
