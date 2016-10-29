package com.mager.story.content.story;

import com.mager.story.constant.EnumConstant.DownloadType;
import com.mager.story.constant.EnumConstant.FileExtension;
import com.mager.story.constant.EnumConstant.FolderType;
import com.mager.story.home.DownloadInterface;
import com.mager.story.home.LoadingInterface;
import com.mager.story.util.FirebaseUtil;

import java.nio.charset.Charset;

/**
 * Created by Gerry on 30/10/2016.
 */

public class StoryDownloader {

    private static final long MAX_SIZE = 1024 * 1024;

    private StoryFragment fragment;
    private DownloadInterface downloadInterface;
    private LoadingInterface loadingInterface;

    public StoryDownloader(StoryFragment fragment, String code) {
        this.fragment = fragment;
        this.downloadInterface = fragment;
        this.loadingInterface = fragment;

        downloadStory(code + FileExtension.STORY);
    }

    private void downloadStory(String fileName) {
        loadingInterface.setLoading(true);

        FirebaseUtil firebaseUtil = new FirebaseUtil();
        firebaseUtil.getStorageWithChild(FolderType.STORY).child(fileName).getBytes(MAX_SIZE)
                .addOnSuccessListener(fragment.getActivity(), bytes -> {
                    String content = new String(bytes, Charset.defaultCharset());
                    downloadInterface.downloadSuccess(content, DownloadType.STORY);
                })
                .addOnFailureListener(fragment.getActivity(), e -> {
                    downloadInterface.downloadFail(e.getMessage());
                });
    }
}
