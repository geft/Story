package com.mager.story.content.story;

import com.mager.story.constant.EnumConstant.DownloadType;
import com.mager.story.constant.EnumConstant.FileExtension;
import com.mager.story.constant.EnumConstant.FolderType;
import com.mager.story.core.callback.Downloadable;
import com.mager.story.core.callback.Loadable;
import com.mager.story.util.FirebaseUtil;

import java.nio.charset.Charset;

/**
 * Created by Gerry on 30/10/2016.
 */

public class StoryDownloader {

    private static final long MAX_SIZE = 1024 * 1024;

    private StoryFragment fragment;
    private Downloadable downloadable;
    private Loadable loadable;

    public StoryDownloader(StoryFragment fragment, String code) {
        this.fragment = fragment;
        this.downloadable = fragment;
        this.loadable = fragment;

        downloadStory(code + FileExtension.STORY);
    }

    private void downloadStory(String fileName) {
        loadable.setLoading(true);

        FirebaseUtil firebaseUtil = new FirebaseUtil();
        firebaseUtil.getStorageWithChild(FolderType.STORY).child(fileName).getBytes(MAX_SIZE)
                .addOnSuccessListener(fragment.getActivity(), bytes -> {
                    String content = new String(bytes, Charset.defaultCharset());
                    downloadable.downloadSuccess(content, DownloadType.STORY);
                })
                .addOnFailureListener(fragment.getActivity(), e -> {
                    downloadable.downloadFail(e.getMessage());
                });
    }
}
