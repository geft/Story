package com.mager.story.util;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.mager.story.core.callback.Downloadable;
import com.mager.story.core.callback.Loadable;
import com.mager.story.data.DownloadInfo;

/**
 * Created by Gerry on 03/11/2016.
 */

public class DownloadUtil {
    public static void downloadBytes(Loadable loadable, Downloadable downloadable, String code, DownloadInfo downloadInfo) {
        String fileName = FileUtil.getFileName(code, downloadInfo);

        try {
            initStorageReference(loadable, fileName, downloadInfo)
                    .getBytes(downloadInfo.maxSize)
                    .addOnSuccessListener(getOnSuccessListener(loadable, downloadable, downloadInfo, fileName))
                    .addOnFailureListener(getOnFailureListener(loadable, downloadable));
        } catch (Exception e) {
            CrashUtil.logError(e);
        }
    }

    public static void downloadUri(Loadable loadable, Downloadable downloadable, String code, DownloadInfo downloadInfo) {
        try {
            initStorageReference(loadable, FileUtil.getFileName(code, downloadInfo), downloadInfo)
                    .getDownloadUrl()
                    .addOnCompleteListener(getUriOnCompleteListener(loadable, downloadable, downloadInfo))
                    .addOnFailureListener(getOnFailureListener(loadable, downloadable));
        } catch (Exception e) {
            CrashUtil.logError(e);
        }
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
