package com.mager.story.common;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mager.story.util.FileUtil;

import java.io.File;

/**
 * Created by Gerry on 08/10/2016.
 */

public class CustomBindingAdapter {

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView view, String url) {
        if (url == null) return;

        Glide.with(view.getContext())
                .load(url)
                .crossFade()
                .fitCenter()
                .into(view);
    }

    @BindingAdapter("imagePath")
    public static void setImagePath(ImageView view, String path) {
        if (path == null) return;

        Glide.with(view.getContext())
                .load(FileUtil.readBytesFromDevice(new File(path)))
                .asBitmap()
                .into(view);
    }
}
