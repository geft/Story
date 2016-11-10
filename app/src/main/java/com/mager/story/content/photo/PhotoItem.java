package com.mager.story.content.photo;

import android.databinding.Bindable;
import android.databinding.ObservableBoolean;

import com.mager.story.BR;
import com.mager.story.core.CoreViewModel;

import org.parceler.Parcel;

/**
 * Created by Gerry on 08/10/2016.
 */

@Parcel
public class PhotoItem extends CoreViewModel {

    public ObservableBoolean loading = new ObservableBoolean(true);
    public ObservableBoolean error = new ObservableBoolean();
    String name;
    String group;
    String url;
    String path;

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

    @Bindable
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        notifyPropertyChanged(BR.url);
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
