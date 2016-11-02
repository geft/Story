package com.mager.story.content.video;

import android.app.Activity;

import com.mager.story.constant.EnumConstant.DownloadType;
import com.mager.story.constant.EnumConstant.FileExtension;
import com.mager.story.constant.EnumConstant.FolderType;
import com.mager.story.core.callback.Downloadable;
import com.mager.story.core.callback.Loadable;
import com.mager.story.util.FirebaseUtil;

/**
 * Created by Gerry on 03/11/2016.
 */

public class VideoDownloader {
    private Activity activity;
    private Downloadable downloadable;
    private Loadable loadable;

    public VideoDownloader(VideoActivity activity, String code) {
        this.activity = activity;
        this.downloadable = activity;
        this.loadable = activity;

        startDownload(code + FileExtension.VIDEO);
    }

    private void startDownload(String fileName) {
        loadable.setLoading(true);

        FirebaseUtil firebaseUtil = new FirebaseUtil();
        firebaseUtil.getStorageWithChild(FolderType.VIDEO).child(fileName).getDownloadUrl()
                .addOnCompleteListener(activity, task -> {
                    downloadable.downloadSuccess(task.getResult(), DownloadType.VIDEO);
                    loadable.setLoading(false);
                })
                .addOnFailureListener(activity, e -> {
                    downloadable.downloadFail(e.getMessage());
                });
    }
}
