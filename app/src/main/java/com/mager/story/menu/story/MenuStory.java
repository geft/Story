package com.mager.story.menu.story;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.drawable.Drawable;

import com.mager.story.BR;

import org.parceler.Parcel;
import org.parceler.Transient;

/**
 * Created by Gerry on 21/10/2016.
 */

@Parcel
public class MenuStory extends BaseObservable {
    protected String chapter;
    protected String title;
    protected String code;

    @Transient
    protected Drawable image;

    @Bindable
    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
        notifyPropertyChanged(BR.chapter);
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
        notifyPropertyChanged(BR.image);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
