package com.mager.story.data;

import android.support.annotation.Nullable;

import com.mager.story.constant.EnumConstant;

/**
 * Created by Gerry on 04/11/2016.
 */

public class DownloadInfo {

    @EnumConstant.FolderType
    public String folderType;

    @EnumConstant.FileExtension
    public String fileExtension;

    @EnumConstant.DownloadType
    public String downloadType;

    @Nullable
    public String group;

    public long maxSize;
}
