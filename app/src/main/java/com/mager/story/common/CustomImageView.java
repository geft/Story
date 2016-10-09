package com.mager.story.common;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Gerry on 08/10/2016.
 */

public class CustomImageView extends ImageView {
    public CustomImageView(Context context) {
        super(context);
    }

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView view, String url) {
        Glide.with(view.getContext())
                .load(url)
                .centerCrop()
                .into(view);
    }
}
