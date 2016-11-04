package com.mager.story.data;

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

    public long maxSize;
}
