package com.mager.story.content.story;

import android.databinding.Bindable;
import android.databinding.ObservableBoolean;

import com.mager.story.BR;
import com.mager.story.core.CoreViewModel;

import org.parceler.Parcel;

/**
 * Created by Gerry on 22/10/2016.
 */

@Parcel
public class StoryViewModel extends CoreViewModel {
    public ObservableBoolean ready = new ObservableBoolean();
    public ObservableBoolean isNightMode = new ObservableBoolean();
    protected String code;
    protected String title;
    protected String chapter;
    protected String content;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        notifyPropertyChanged(BR.content);
    }
}
