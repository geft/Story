package com.mager.story.content.photo;

import android.databinding.Bindable;

import com.mager.story.BR;
import com.mager.story.core.CoreViewModel;

import org.parceler.Parcel;

/**
 * Created by Gerry on 08/10/2016.
 */

@Parcel
public class PhotoItem extends CoreViewModel {
    protected String url;
    protected String name;
    protected String group;

    @Bindable
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        notifyPropertyChanged(BR.url);
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
