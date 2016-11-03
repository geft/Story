package com.mager.story.login;

import com.google.gson.Gson;
import com.mager.story.constant.EnumConstant;
import com.mager.story.constant.EnumConstant.FolderType;
import com.mager.story.core.callback.Downloadable;
import com.mager.story.datamodel.MenuDataModel;
import com.mager.story.util.CrashUtil;
import com.mager.story.util.FileUtil;
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

    private MenuDataModel dataModel;
    private FirebaseUtil firebaseUtil;
    private Downloadable downloadable;

    public MenuDownloader(Downloadable downloadable, FirebaseUtil firebaseUtil) {
        this.firebaseUtil = firebaseUtil;
        this.downloadable = downloadable;
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
        File file = FileUtil.createFileInFolder(FolderType.MENU, name);

        firebaseUtil.getStorageWithChild(FolderType.MENU).child(name).getFile(file)
                .addOnFailureListener((e) -> setError(e, file.getPath()))
                .addOnSuccessListener(task -> downloadable.downloadSuccess(null, downloadType));
    }

    void getMenuDataModel() {
        firebaseUtil.getStorageWithChild(FILE_MENU_JSON).getBytes(MAX_SIZE_JSON)
                .addOnCompleteListener(task -> {
                    try {
                        String json = new String(task.getResult(), StandardCharsets.UTF_8);
                        downloadable.downloadSuccess(
                                new Gson().fromJson(json, MenuDataModel.class),
                                EnumConstant.DownloadType.MENU_JSON);
                    } catch (Exception e) {
                        setError(e, FILE_MENU_JSON);
                    }
                })
                .addOnFailureListener((e) -> setError(e, FILE_MENU_JSON));
    }

    private void setError(Exception e, String path) {
        CrashUtil.logWarning(EnumConstant.Tag.LOGIN, path);
        firebaseUtil.notifyDownloadError(downloadable, e.getMessage());
    }
}
