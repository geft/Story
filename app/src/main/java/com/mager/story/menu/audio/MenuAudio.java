package com.mager.story.menu.audio;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.mager.story.BR;

import org.parceler.Parcel;

/**
 * Created by Gerry on 21/10/2016.
 */

@Parcel
public class MenuAudio extends BaseObservable {
    protected String name;

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }
}
