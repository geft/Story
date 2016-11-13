package com.mager.story.login;

import android.app.Activity;

import com.mager.story.constant.Constants;
import com.mager.story.constant.EnumConstant;
import com.mager.story.core.callback.Downloadable;
import com.mager.story.core.callback.Loadable;
import com.mager.story.data.DownloadInfo;
import com.mager.story.data.DownloadInfoUtil;
import com.mager.story.data.MenuData;
import com.mager.story.util.DownloadUtil;
import com.mager.story.util.FileUtil;

import java.io.File;

/**
 * Created by Gerry on 29/10/2016.
 */

class MenuDownloader {

    private Activity activity;
    private Loadable loadable;
    private Downloadable downloadable;

    public MenuDownloader(Activity activity, Loadable loadable, Downloadable downloadable) {
        this.activity = activity;
        this.loadable = loadable;
        this.downloadable = downloadable;
    }

    void downloadMenuPhoto(MenuData menuData) {
        initPhotoDownload(menuData);
        initStoryDownload(menuData);
    }

    private void initPhotoDownload(MenuData menuData) {
        for (MenuData.Photo photo : menuData.photo) {
            downloadMenuPhoto(photo.code, EnumConstant.DownloadType.MENU_PHOTO);
        }
    }

    private void initStoryDownload(MenuData menuData) {
        for (MenuData.Story story : menuData.story) {
            downloadMenuPhoto(story.code, EnumConstant.DownloadType.MENU_STORY);
        }
    }

    private void downloadMenuPhoto(String code, @EnumConstant.DownloadType String downloadType) {
        DownloadInfo downloadInfo = DownloadInfoUtil.getMenuPhotoInfo(downloadType);
        File file = FileUtil.INSTANCE.getFileFromCode(code, downloadInfo);
        if (file.exists()) {
            downloadable.downloadSuccess(null, downloadType);
        } else {
            DownloadUtil.downloadBytes(
                    loadable, getDownloadable(code, downloadInfo), code, downloadInfo);
        }
    }

    private Downloadable getDownloadable(String code, DownloadInfo downloadInfo) {
        return new Downloadable() {
            @Override
            public void downloadSuccess(Object file, @EnumConstant.DownloadType String downloadType) {
                if (file instanceof byte[]) {
                    FileUtil.INSTANCE.saveBytesToDevice((byte[]) file, code, downloadInfo, false);
                    downloadable.downloadSuccess(file, downloadType);
                }
            }

            @Override
            public void downloadFail(String message) {
                downloadable.downloadFail(message);
            }
        };
    }

    void downloadMenuJson() {
        DownloadUtil.downloadBytes(loadable, downloadable,
                Constants.MENU_JSON, DownloadInfoUtil.getMenuJsonInfo());
    }
}
