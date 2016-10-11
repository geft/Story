package com.mager.story.photo;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mager.story.R;
import com.mager.story.databinding.DialogPhotoBinding;

import uk.co.senab.photoview.PhotoViewAttacher;

import static android.view.WindowManager.LayoutParams.FLAG_SECURE;

/**
 * Created by Gerry on 11/10/2016.
 */

class PhotoDialog extends Dialog {

    private DialogPhotoBinding binding;
    private String url;

    PhotoDialog(Context context, String url) {
        super(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);

        this.url = url;
        initBinding();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initFlags();
        initListeners();
        initImage();
    }

    private void initFlags() {
        if (getWindow() != null) {
            getWindow().setFlags(FLAG_SECURE, FLAG_SECURE);
        }
    }

    private void initListeners() {
        binding.errorText.setOnClickListener(view -> dismiss());
        binding.image.setOnClickListener(view -> dismiss());
    }

    private void initBinding() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_photo, null, false);
        setContentView(binding.getRoot());
    }

    private void initImage() {
        Glide
                .with(getContext())
                .load(url)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        e.printStackTrace();

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
}
