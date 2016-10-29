package com.mager.story.home;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.mager.story.StoryApplication;
import com.mager.story.constant.EnumConstant;
import com.mager.story.constant.EnumConstant.FolderType;
import com.mager.story.datamodel.MenuDataModel;
import com.mager.story.util.FirebaseUtil;

import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * Created by Gerry on 29/10/2016.
 */

class MenuDownloader {
    private static final long MAX_SIZE_JSON = 1024 * 16;
    private static final String FILE_MENU_JSON = "menu.json";
    private static final String EXT = ".jpg";

    private HomeActivity activity;
    private MenuDataModel dataModel;
    private FirebaseUtil firebaseUtil;
    private DownloadInterface downloadInterface;

    public MenuDownloader(HomeActivity activity) {
        this.activity = activity;
        this.firebaseUtil = new FirebaseUtil();
        this.downloadInterface = activity;
    }

    void initMenuImageDownload(MenuDataModel dataModel) {
        this.dataModel = dataModel;

        initPhotoDownload();
        initStoryDownload();
    }

    private void initPhotoDownload() {
        for (MenuDataModel.Photo photo : dataModel.photo) {
            downloadMenuImage(
                    EnumConstant.FilePrefix.PHOTO + photo.code + EXT,
                    EnumConstant.DownloadType.MENU_PHOTO
            );
        }
    }

    private void initStoryDownload() {
        for (MenuDataModel.Story story : dataModel.story) {
            downloadMenuImage(
                    EnumConstant.FilePrefix.STORY + story.code + EXT,
                    EnumConstant.DownloadType.MENU_STORY
            );
        }
    }

    private void downloadMenuImage(String name, String downloadType) {
        try {
            File file = createFileInDirectory(FolderType.MENU, name);

            firebaseUtil.getStorageWithChild(FolderType.MENU).child(name).getFile(file)
                    .addOnProgressListener(taskSnapshot -> downloadInterface.downloadUpdate(
                            taskSnapshot.getBytesTransferred(),
                            taskSnapshot.getTotalByteCount(), downloadType))
                    .addOnFailureListener(e -> firebaseUtil.notifyDownloadError(downloadInterface, e.getMessage()))
                    .addOnSuccessListener(activity, task -> downloadInterface.downloadSuccess(null, downloadType));
        } catch (Exception e) {
            firebaseUtil.notifyDownloadError(downloadInterface, e.getMessage());
        }
    }

    void getMenuDataModel() {
        firebaseUtil.getStorageWithChild(FILE_MENU_JSON).getBytes(MAX_SIZE_JSON)
                .addOnCompleteListener(activity, task -> {
                    try {
                        String json = new String(task.getResult(), StandardCharsets.UTF_8);
                        downloadInterface.downloadSuccess(
                                new Gson().fromJson(json, MenuDataModel.class),
                                EnumConstant.DownloadType.MENU_JSON);
                    } catch (Exception e) {
                        firebaseUtil.notifyDownloadError(downloadInterface, e.getMessage());
                    }
                })
                .addOnFailureListener(e -> firebaseUtil.notifyDownloadError(downloadInterface, e.getMessage()));
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @NonNull
    private File createFileInDirectory(String folder, String name) {
        File dir = StoryApplication.getInstance().getFilesDir();
        File subDir = new File(dir + File.separator + folder);
        subDir.mkdirs();

        return new File(subDir, name);
    }
}
