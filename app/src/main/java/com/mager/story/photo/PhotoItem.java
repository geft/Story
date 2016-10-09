package com.mager.story.photo;

import android.content.res.Resources;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mager.story.BR;
import com.mager.story.core.CoreViewModel;


import org.parceler.Parcel;

/**
 * Created by Gerry on 08/10/2016.
 */

public class PhotoItem extends CoreViewModel {
    public String url;

    @Bindable
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        notifyPropertyChanged(BR.url);
    }
}
