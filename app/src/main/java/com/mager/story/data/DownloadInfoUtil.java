package com.mager.story.data;

import com.mager.story.constant.Constants;
import com.mager.story.constant.EnumConstant.DownloadType;
import com.mager.story.constant.EnumConstant.FileExtension;
import com.mager.story.constant.EnumConstant.FolderType;
import com.mager.story.content.photo.PhotoItem;

/**
 * Created by Gerry on 04/11/2016.
 */
public class DownloadInfoUtil {
    public static DownloadInfo getMenuJsonInfo() {
        DownloadInfo info = new DownloadInfo();
        info.folderType = FolderType.JSON;
        info.fileExtension = FileExtension.JSON;
        info.downloadType = DownloadType.MENU_JSON;
        info.maxSize = Constants.MAX_MENU_SIZE;

        return info;
    }

    public static DownloadInfo getMenuPhotoInfo(@DownloadType String downloadType) {
        DownloadInfo info = new DownloadInfo();
        info.folderType = FolderType.MENU;
        info.downloadType = downloadType;
        info.maxSize = Constants.MAX_MENU_SIZE;

        switch (downloadType) {
            case DownloadType.MENU_PHOTO:
                info.fileExtension = FileExtension.MENU_PHOTO;
                break;
            case DownloadType.MENU_STORY:
                info.fileExtension = FileExtension.MENU_STORY;
        }

        return info;
    }

    public static DownloadInfo getPhotoInfo(PhotoItem photoItem, boolean isFull) {
        DownloadInfo info = new DownloadInfo();
        info.folderType = FolderType.PHOTO;
        info.fileExtension = FileExtension.PHOTO;
        info.group = photoItem.getGroup();

        if (isFull) {
            info.downloadType = DownloadType.PHOTO_FULL;
            info.maxSize = Constants.MAX_PHOTO_SIZE_FULL;
        } else {
            info.downloadType = DownloadType.PHOTO_THUMB;
            info.maxSize = Constants.MAX_PHOTO_SIZE_THUMB;
        }

        return info;
    }

    public static DownloadInfo getStoryInfo() {
        DownloadInfo info = new DownloadInfo();
        info.folderType = FolderType.STORY;
        info.fileExtension = FileExtension.STORY;
        info.downloadType = DownloadType.STORY;
        info.maxSize = Constants.MAX_STORY_SIZE;

        return info;
    }

    public static DownloadInfo getAudioInfo() {
        DownloadInfo info = new DownloadInfo();
        info.folderType = FolderType.AUDIO;
        info.fileExtension = FileExtension.AUDIO;
        info.downloadType = DownloadType.AUDIO;
        info.maxSize = Constants.MAX_AUDIO_SIZE;

        return info;
    }

    public static DownloadInfo getVideoInfo() {
        DownloadInfo info = new DownloadInfo();
        info.folderType = FolderType.VIDEO;
        info.fileExtension = FileExtension.VIDEO;
        info.downloadType = DownloadType.VIDEO;
        info.maxSize = Constants.MAX_VIDEO_SIZE;

        return info;
    }
}
