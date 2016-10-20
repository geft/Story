package com.mager.story.menu.photo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.drawable.Drawable;

import com.mager.story.BR;
import com.mager.story.constant.EnumConstant;

import org.parceler.Parcel;
import org.parceler.Transient;

/**
 * Created by Gerry on 20/10/2016.
 */

@Parcel
public class MenuItemPhoto extends BaseObservable {

    protected int count;
    protected String name;

    @EnumConstant.PhotoGroup
    protected String photoGroup;

    @Transient
    protected Drawable image;

    @Bindable
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        notifyPropertyChanged(BR.count);
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    public String getPhotoGroup() {
        return photoGroup;
    }

    public void setPhotoGroup(String photoGroup) {
        this.photoGroup = photoGroup;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }
}
