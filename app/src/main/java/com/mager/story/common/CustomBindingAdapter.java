package com.mager.story.common;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Gerry on 08/10/2016.
 */

public class CustomBindingAdapter {

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView view, String url) {
        Glide.with(view.getContext())
                .load(url)
                .fitCenter()
                .into(view);
    }
}
