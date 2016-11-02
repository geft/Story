package com.mager.story.content.video;

import android.databinding.Bindable;

import com.mager.story.BR;
import com.mager.story.core.CoreViewModel;

import org.parceler.Parcel;

/**
 * Created by Gerry on 29/10/2016.
 */

@Parcel
public class VideoViewModel extends CoreViewModel {

    protected boolean loading;
    protected boolean ready;

    @Bindable
    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
        notifyPropertyChanged(BR.loading);
    }

    @Bindable
    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
        notifyPropertyChanged(BR.ready);
    }
}
