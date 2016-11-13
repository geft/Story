package com.mager.story.content.photo;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.mager.story.constant.EnumConstant;
import com.mager.story.core.callback.Downloadable;
import com.mager.story.core.callback.Loadable;
import com.mager.story.data.DownloadInfo;
import com.mager.story.data.DownloadInfoUtil;
import com.mager.story.util.CommonUtil;
import com.mager.story.util.DownloadUtil;
import com.mager.story.util.FileUtil;
import com.mager.story.util.LogUtil;

import java.io.File;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Gerry on 11/10/2016.
 */

class PhotoDownloader {
    private Activity activity;
    private CompositeSubscription subscription;

    PhotoDownloader(Activity activity, CompositeSubscription subscription) {
        this.activity = activity;
        this.subscription = subscription;
    }

    void setPaths(List<PhotoItem> list) {
        subscription.add(
                Observable.from(list)
                        .map(this::handlePhotoItem)
                        .toList()
                        .compose(CommonUtil.getCommonTransformer())
                        .subscribe()
        );
    }

    @NonNull
    private Boolean handlePhotoItem(PhotoItem photoItem) {
        DownloadInfo downloadInfo = DownloadInfoUtil.getPhotoInfo(photoItem, false);
        File file = FileUtil.INSTANCE.getFileFromCode(photoItem.getName(), downloadInfo);

        if (file.exists()) {
            setItemPath(photoItem, downloadInfo);
            return true;
        }

        DownloadUtil.downloadBytes(
                getLoadable(photoItem),
                getDownloadable(photoItem, downloadInfo),
                photoItem.getName(),
                downloadInfo
        );

        return false;
    }

    private Loadable getLoadable(PhotoItem item) {
        return new Loadable() {
            @Override
            public boolean isLoading() {
                return item.loading.get();
            }

            @Override
            public void setLoading(boolean loading) {
                item.loading.set(loading);
            }

            @Override
            public void setError(String message) {
                item.loading.set(false);
                item.error.set(true);
            }
        };
    }

    private Downloadable getDownloadable(PhotoItem item, DownloadInfo downloadInfo) {
        String code = item.getName();

        return new Downloadable() {
            @Override
            public void downloadSuccess(Object file, @EnumConstant.DownloadType String downloadType) {
                if (file instanceof byte[]) {
                    FileUtil.INSTANCE.saveBytesToDevice((byte[]) file, code, downloadInfo, false);
                    setItemPath(item, downloadInfo);
                }
            }

            @Override
            public void downloadFail(String message) {
                item.error.set(true);
                LogUtil.INSTANCE.logWarning(EnumConstant.Tag.PHOTO, message);
            }
        };
    }

    private void setItemPath(PhotoItem item, DownloadInfo downloadInfo) {
        subscription.add(
                Observable.just(FileUtil.INSTANCE.getFileFromCode(item.getName(), downloadInfo).getPath())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(item::setPath)
        );
    }
}
