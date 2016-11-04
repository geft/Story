package com.mager.story.util;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.mager.story.constant.EnumConstant;
import com.mager.story.constant.EnumConstant.DownloadType;
import com.mager.story.constant.EnumConstant.FilePrefix;
import com.mager.story.content.photo.PhotoItem;
import com.mager.story.core.callback.Downloadable;
import com.mager.story.core.callback.Loadable;
import com.mager.story.data.DownloadInfo;

/**
 * Created by Gerry on 03/11/2016.
 */

public class DownloadUtil {

    public static void downloadUriIntoPhotoItem(Activity activity, Loadable loadable, DownloadInfo downloadInfo, PhotoItem photoItem) {
        initStorageReference(loadable, getFileName(photoItem.getName(), downloadInfo), downloadInfo)
                .getDownloadUrl()
                .addOnCompleteListener(activity, task -> {
                    loadable.setLoading(false);

                    switch (downloadInfo.downloadType) {
                        case DownloadType.PHOTO_THUMB:
                            photoItem.setUrl(task.getResult().toString());
                            break;
                        case DownloadType.PHOTO_FULL:
                            photoItem.setFullUrl(task.getResult().toString());
                            break;
                    }

                })
                .addOnFailureListener(activity, e -> CrashUtil.logWarning(EnumConstant.Tag.PHOTO, e.getMessage()));
    }

    public static void downloadBytes(Activity activity, Loadable loadable, Downloadable downloadable, String code, DownloadInfo downloadInfo) {
        String fileName = getFileName(code, downloadInfo);

        initStorageReference(loadable, fileName, downloadInfo)
                .getBytes(downloadInfo.maxSize)
                .addOnSuccessListener(activity, getOnSuccessListener(loadable, downloadable, downloadInfo, fileName))
                .addOnFailureListener(activity, getOnFailureListener(loadable, downloadable));
    }

    public static void downloadUri(Activity activity, Loadable loadable, Downloadable downloadable, String code, DownloadInfo downloadInfo) {
        initStorageReference(loadable, getFileName(code, downloadInfo), downloadInfo)
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
            downloadable.downloadSuccess(task.getResult(), downloadInfo.downloadType);
            loadable.setLoading(false);
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
