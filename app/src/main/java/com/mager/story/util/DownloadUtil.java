package com.mager.story.util;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mager.story.constant.EnumConstant.DownloadType;
import com.mager.story.constant.EnumConstant.FilePrefix;
import com.mager.story.core.callback.Downloadable;
import com.mager.story.core.callback.Loadable;
import com.mager.story.data.DownloadInfo;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Gerry on 03/11/2016.
 */

public class DownloadUtil {

    public static void loadPhotoFromUrl(Context context, Loadable loadable, String url, ImageView image, boolean usePhotoView) {
        loadable.setLoading(true);

        Glide.with(context).load(url)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        loadable.setLoading(false);
                        if (e != null) loadable.setError(e.getMessage());
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        loadable.setLoading(false);
                        if (usePhotoView) new PhotoViewAttacher(image);
                        return false;
                    }
                })
                .into(image);
    }

    public static void downloadBytes(Activity activity, Loadable loadable, Downloadable downloadable, String code, DownloadInfo downloadInfo) {
        loadable.setLoading(true);
        String fileName = getFileName(code, downloadInfo);

        new FirebaseUtil()
                .getStorageWithChild(downloadInfo.folderType)
                .child(fileName)
                .getBytes(downloadInfo.maxSize)
                .addOnSuccessListener(activity, bytes -> {
                    FileUtil.createFileInFolder(downloadInfo.folderType, fileName);
                    downloadable.downloadSuccess(bytes, downloadInfo.downloadType);
                    loadable.setLoading(false);
                })
                .addOnFailureListener(activity, e -> {
                    downloadable.downloadFail(e.getMessage());
                });
    }

    public static void downloadUri(Activity activity, Loadable loadable, Downloadable downloadable, String code, DownloadInfo downloadInfo) {
        loadable.setLoading(true);
        String fileName = getFileName(code, downloadInfo);

        new FirebaseUtil()
                .getStorageWithChild(downloadInfo.folderType)
                .child(fileName)
                .getDownloadUrl()
                .addOnCompleteListener(activity, task -> {
                    downloadable.downloadSuccess(task.getResult(), downloadInfo.downloadType);
                    loadable.setLoading(false);
                })
                .addOnFailureListener(activity, e -> {
                    downloadable.downloadFail(e.getMessage());
                });
    }

    @NonNull
    private static String getFileName(String code, DownloadInfo downloadInfo) {
        String prefix = "";

        switch (downloadInfo.downloadType) {
            case DownloadType.MENU_JSON:
                break;
            case DownloadType.MENU_PHOTO:
                prefix = FilePrefix.MENU_PHOTO;
                break;
            case DownloadType.MENU_STORY:
                prefix = FilePrefix.MENU_STORY;
                break;
            case DownloadType.PHOTO_THUMB:
                prefix = FilePrefix.PHOTO_THUMB;
                break;
            case DownloadType.PHOTO_FULL:
                prefix = FilePrefix.PHOTO_FULL;
                break;
            case DownloadType.STORY:
                break;
        }

        return prefix + code + downloadInfo.fileExtension;
    }
}
