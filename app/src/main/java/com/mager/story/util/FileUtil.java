package com.mager.story.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mager.story.R;
import com.mager.story.StoryApplication;
import com.mager.story.constant.EnumConstant;
import com.mager.story.constant.EnumConstant.FolderType;
import com.mager.story.constant.EnumConstant.Tag;
import com.mager.story.data.DownloadInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by Gerry on 31/10/2016.
 */

public class FileUtil {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void saveBytesToDevice(byte[] bytes, String code, DownloadInfo downloadInfo, boolean isMedia) {
        File file = getFileFromCode(code, downloadInfo);

        if (file.exists()) file.delete();

        if (downloadInfo.group != null) {
            file.getParentFile().mkdirs();
        }

        try (FileOutputStream stream = new FileOutputStream(file.getPath())) {
            if (isMedia) {
                stream.write(EncryptionUtil.flipFirst100(bytes));
            } else {
                stream.write(EncryptionUtil.flip(bytes));
            }
            stream.close();
            CrashUtil.logInfo(downloadInfo.downloadType, ResourceUtil.getString(R.string.file_save_success, file.getPath()));
        } catch (Exception e) {
            CrashUtil.logError(downloadInfo.downloadType, ResourceUtil.getString(R.string.file_save_error, file.getPath()), e);
        }
    }

    @Nullable
    public static byte[] readBytesFromDevice(File file, boolean isMedia) {
        if (file.exists()) {
            try {
                byte[] bytes;

                if (isMedia) {
                    bytes = EncryptionUtil.flipFirst100(readFile(file));
                } else {
                    bytes = EncryptionUtil.flip(readFile(file));
                }
                CrashUtil.logInfo(Tag.FILE, ResourceUtil.getString(R.string.file_load_success, file.getPath()));
                return bytes;
            } catch (Exception e) {
                CrashUtil.logError(Tag.FILE, ResourceUtil.getString(R.string.file_load_error, file.getPath()), e);
            }
        }

        return null;
    }

    private static byte[] readFile(File file) throws IOException {
        // Open file
        try (RandomAccessFile f = new RandomAccessFile(file, "r")) {
            // Get and check length
            long longLength = f.length();
            int length = (int) longLength;
            if (length != longLength)
                throw new IOException("File size >= 2 GB");
            // Read file and return data
            byte[] data = new byte[length];
            f.readFully(data);
            return data;
        }
    }

    private static File getFileFromFileName(DownloadInfo downloadInfo, String fileName) {
        File file;
        if (downloadInfo.group != null) {
            file = new File(getSubFolder(downloadInfo.folderType) + File.separator + downloadInfo.group, fileName);
        } else {
            file = new File(getSubFolder(downloadInfo.folderType), fileName);
        }

        return file;
    }

    public static File getFileFromCode(String code, DownloadInfo downloadInfo) {
        return getFileFromFileName(downloadInfo, getFileName(code, downloadInfo));
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void removeFolderContent(@FolderType String folderType) {
        deleteRecursive(getSubFolder(folderType));
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

    public static void clearInternalData() {
        deleteRecursive(StoryApplication.getInstance().getFilesDir());
    }

    public static void clearCache() {
        deleteRecursive(StoryApplication.getInstance().getCacheDir());
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void deleteRecursive(File fileOrDirectory) {

        if (fileOrDirectory.isDirectory()) {
            for (File child : fileOrDirectory.listFiles()) {
                deleteRecursive(child);
            }
        }

        fileOrDirectory.delete();
    }
}