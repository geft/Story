package com.mager.story.util;

import android.support.annotation.NonNull;

import com.mager.story.StoryApplication;
import com.mager.story.constant.EnumConstant.FolderType;

import java.io.File;

/**
 * Created by Gerry on 31/10/2016.
 */

public class FileUtil {

    public static boolean doesFileExist(@FolderType String folderType, String name) {
        File file = new File(getSubFolder(folderType), name);

        return file.exists();
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
}