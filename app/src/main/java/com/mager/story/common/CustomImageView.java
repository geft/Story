package com.mager.story.common;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mager.story.R;

/**
 * Created by Gerry on 08/10/2016.
 */

public class CustomImageView extends ImageView {

    public CustomImageView(Context context) {
        super(context);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView view, String url) {
        Glide.with(view.getContext())
                .load(url)
                .placeholder(R.drawable.ic_loading)
                .animate(android.R.anim.fade_in)
                .fitCenter()
                .into(view);
    }
}
