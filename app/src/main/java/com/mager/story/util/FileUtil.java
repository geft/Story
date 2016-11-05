package com.mager.story.util;

import android.support.annotation.NonNull;

import com.mager.story.StoryApplication;
import com.mager.story.constant.EnumConstant;
import com.mager.story.constant.EnumConstant.FolderType;
import com.mager.story.data.DownloadInfo;

import java.io.File;

/**
 * Created by Gerry on 31/10/2016.
 */

public class FileUtil {
    public static File getFileFromFileName(DownloadInfo downloadInfo, String fileName) {
        File file;
        if (downloadInfo.group != null) {
            file = new File(getSubFolder(downloadInfo.folderType) + File.separator + downloadInfo.group, fileName);
        } else {
            file = new File(getSubFolder(downloadInfo.folderType), fileName);
        }

        return file;
    }

    public static File getFileFromCode(DownloadInfo downloadInfo, String code) {
        return getFileFromFileName(downloadInfo, getFileName(code, downloadInfo));
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void removeFolderContent(@FolderType String folderType) {
        File dir = getSubFolder(folderType);
        if (dir.isDirectory()) {
            String[] fileList = dir.list();
            for (String file : fileList) {
                new File(dir, file).delete();
            }
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @NonNull
    public static File createFileInFolder(@FolderType String folderType, String name) {
        File subDir = getSubFolder(folderType);
        subDir.mkdirs();

        return new File(subDir, name);
    }

    private static File getFilesDir() {
        return StoryApplication.getInstance().getFilesDir();
    }

    private static File getSubFolder(@FolderType String folderType) {
        return new File(getFilesDir() + File.separator + folderType);
    }

    @NonNull
    public static String getFileName(String code, DownloadInfo downloadInfo) {
        String prefix = "";

        switch (downloadInfo.downloadType) {
            case EnumConstant.DownloadType.MENU_JSON:
                break;
            case EnumConstant.DownloadType.MENU_PHOTO:
                prefix = EnumConstant.FilePrefix.MENU_PHOTO;
                break;
            case EnumConstant.DownloadType.MENU_STORY:
                prefix = EnumConstant.FilePrefix.MENU_STORY;
                break;
            case EnumConstant.DownloadType.PHOTO_THUMB:
                prefix = EnumConstant.FilePrefix.PHOTO_THUMB;
                break;
            case EnumConstant.DownloadType.PHOTO_FULL:
                prefix = EnumConstant.FilePrefix.PHOTO_FULL;
                break;
            case EnumConstant.DownloadType.STORY:
                break;
        }

        return prefix + code + downloadInfo.fileExtension;
    }
}