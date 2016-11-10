package com.mager.story.menu.photo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.mager.story.BR;

import org.parceler.Parcel;

/**
 * Created by Gerry on 20/10/2016.
 */

@Parcel
public class MenuPhoto extends BaseObservable {

    protected int count;
    protected String name;
    protected String code;
    protected String path;

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        notifyPropertyChanged(BR.count);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Bindable
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
        notifyPropertyChanged(BR.path);
    }
}
