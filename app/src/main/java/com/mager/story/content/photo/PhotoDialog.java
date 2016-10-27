package com.mager.story.content.photo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mager.story.R;
import com.mager.story.constant.EnumConstant;
import com.mager.story.core.CoreDialogFragment;
import com.mager.story.databinding.DialogPhotoBinding;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Gerry on 11/10/2016.
 */

public class PhotoDialog extends CoreDialogFragment {

    private DialogPhotoBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_photo, null, false);

        return binding.getRoot();
    }

    void loadImage(String url) {
        Glide.with(this)
                .load(url)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        if (e != null) {
                            e.printStackTrace();
                        }

                        binding.progress.setVisibility(View.GONE);
                        binding.errorText.setVisibility(View.VISIBLE);

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        binding.progress.setVisibility(View.GONE);
                        new PhotoViewAttacher(binding.image);

                        return false;
                    }
                })
                .into(binding.image);
    }

    @Override
    protected String getDialogStyle() {
        return EnumConstant.DialogStyle.FULL_SCREEN;
    }
}
