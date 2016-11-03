package com.mager.story.util;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mager.story.constant.CommonConstant;
import com.mager.story.constant.EnumConstant;
import com.mager.story.core.callback.Downloadable;
import com.mager.story.core.callback.Loadable;

import java.nio.charset.Charset;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Gerry on 03/11/2016.
 */

public class DownloadUtil {

    public static void downloadImage(Context context, Loadable loadable, String url, ImageView image, boolean usePhotoView) {
        loadable.setLoading(true);

        Glide.with(context)
                .load(url)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        loadable.setLoading(false);
                        if (e != null) {
                            loadable.setError(e.getMessage());
                        }

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        loadable.setLoading(false);

                        if (usePhotoView) {
                            new PhotoViewAttacher(image);
                        }

                        return false;
                    }
                })
                .into(image);
    }

    public static void downloadVideo(Activity activity, Loadable loadable, Downloadable downloadable, String code) {
        String fileName = code + EnumConstant.FileExtension.VIDEO;
        loadable.setLoading(true);

        FirebaseUtil firebaseUtil = new FirebaseUtil();
        firebaseUtil.getStorageWithChild(EnumConstant.FolderType.VIDEO).child(fileName).getDownloadUrl()
                .addOnCompleteListener(activity, task -> {
                    downloadable.downloadSuccess(task.getResult(), EnumConstant.DownloadType.VIDEO);
                    loadable.setLoading(false);
                })
                .addOnFailureListener(activity, e -> {
                    downloadable.downloadFail(e.getMessage());
                });
    }

    public static void downloadStory(Activity activity, Loadable loadable, Downloadable downloadable, String code) {
        String fileName = code + EnumConstant.FileExtension.STORY;
        loadable.setLoading(true);

        FirebaseUtil firebaseUtil = new FirebaseUtil();
        firebaseUtil.getStorageWithChild(EnumConstant.FolderType.STORY).child(fileName).getBytes(CommonConstant.MAX_STORY_SIZE)
                .addOnSuccessListener(activity, bytes -> {
                    String content = new String(bytes, Charset.defaultCharset());
                    downloadable.downloadSuccess(content, EnumConstant.DownloadType.STORY);
                    loadable.setLoading(false);
                })
                .addOnFailureListener(activity, e -> {
                    downloadable.downloadFail(e.getMessage());
                });
    }
}
