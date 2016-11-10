package com.mager.story.menu.story;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.mager.story.BR;

import org.parceler.Parcel;

/**
 * Created by Gerry on 21/10/2016.
 */

@Parcel
public class MenuStory extends BaseObservable {
    protected String chapter;
    protected String title;
    protected String code;
    protected String path;

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
