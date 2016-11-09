package com.mager.story.util;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.mager.story.constant.EnumConstant;
import com.mager.story.constant.EnumConstant.DownloadType;
import com.mager.story.content.photo.PhotoItem;
import com.mager.story.core.callback.Downloadable;
import com.mager.story.core.callback.Loadable;
import com.mager.story.data.DownloadInfo;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Gerry on 03/11/2016.
 */

public class DownloadUtil {

    public static void downloadUrlIntoImageView(Context context, Loadable loadable, String url, ImageView image, boolean usePhotoView) {
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

    public static void downloadUriIntoPhotoItem(Activity activity, Loadable loadable, DownloadInfo downloadInfo, PhotoItem photoItem) {
        try {
            initStorageReference(loadable, FileUtil.getFileName(photoItem.getName(), downloadInfo), downloadInfo)
                    .getDownloadUrl()
                    .addOnCompleteListener(activity, task -> {
                        loadable.setLoading(false);

                        switch (downloadInfo.downloadType) {
                            case DownloadType.PHOTO_THUMB:
                                photoItem.setUrl(task.getResult().toString());
                                break;
                        }

                    })
                    .addOnFailureListener(activity, e -> CrashUtil.logWarning(EnumConstant.Tag.PHOTO, e.getMessage()));
        } catch (Exception e) {
            CrashUtil.logWarning(EnumConstant.Tag.PHOTO, e.getMessage());
        }
    }

    public static void downloadBytes(Activity activity, Loadable loadable, Downloadable downloadable, String code, DownloadInfo downloadInfo) {
        String fileName = FileUtil.getFileName(code, downloadInfo);

        initStorageReference(loadable, fileName, downloadInfo)
                .getBytes(downloadInfo.maxSize)
                .addOnSuccessListener(activity, getOnSuccessListener(loadable, downloadable, downloadInfo, fileName))
                .addOnFailureListener(activity, getOnFailureListener(loadable, downloadable));
    }

    public static void downloadUri(Activity activity, Loadable loadable, Downloadable downloadable, String code, DownloadInfo downloadInfo) {
        initStorageReference(loadable, FileUtil.getFileName(code, downloadInfo), downloadInfo)
                .getDownloadUrl()
                .addOnCompleteListener(activity, getUriOnCompleteListener(loadable, downloadable, downloadInfo))
                .addOnFailureListener(activity, getOnFailureListener(loadable, downloadable));
    }

    @NonNull
    private static OnSuccessListener<byte[]> getOnSuccessListener(Loadable loadable, Downloadable downloadable, DownloadInfo downloadInfo, String fileName) {
        return bytes -> {
            FileUtil.createFileInFolder(downloadInfo.folderType, fileName);
            downloadable.downloadSuccess(bytes, downloadInfo.downloadType);
            loadable.setLoading(false);
        };
    }

    @NonNull
    private static OnFailureListener getOnFailureListener(Loadable loadable, Downloadable downloadable) {
        return e -> {
            downloadable.downloadFail(e.getMessage());
            loadable.setLoading(false);
        };
    }

    @NonNull
    private static OnCompleteListener<Uri> getUriOnCompleteListener(Loadable loadable, Downloadable downloadable, DownloadInfo downloadInfo) {
        return task -> {
            loadable.setLoading(false);
            if (task.isSuccessful()) {
                downloadable.downloadSuccess(task.getResult(), downloadInfo.downloadType);
            } else {
                try {
                    downloadable.downloadFail(task.getResult().toString());
                } catch (Exception e) {
                    downloadable.downloadFail(e.getMessage());
                }
            }
        };
    }

    @NonNull
    private static StorageReference initStorageReference(Loadable loadable, String fileName, DownloadInfo downloadInfo) {
        loadable.setLoading(true);

        if (downloadInfo.group != null) {
            return new FirebaseUtil()
                    .getStorageWithChild(downloadInfo.folderType)
                    .child(downloadInfo.group)
                    .child(fileName);
        } else {
            return new FirebaseUtil()
                    .getStorageWithChild(downloadInfo.folderType)
                    .child(fileName);
        }
    }
}
