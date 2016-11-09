package com.mager.story.content.photo;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.mager.story.R;
import com.mager.story.constant.EnumConstant;
import com.mager.story.core.callback.Downloadable;
import com.mager.story.core.callback.Loadable;
import com.mager.story.data.DownloadInfo;
import com.mager.story.data.DownloadInfoUtil;
import com.mager.story.util.CommonUtil;
import com.mager.story.util.DownloadUtil;
import com.mager.story.util.FileUtil;
import com.mager.story.util.ResourceUtil;

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
    private Loadable loadable;
    private CompositeSubscription subscription;

    PhotoDownloader(Activity activity, Loadable loadable, CompositeSubscription subscription) {
        this.activity = activity;
        this.loadable = loadable;
        this.subscription = subscription;
    }

    void setPaths(List<PhotoItem> list) {
        subscription.add(
                Observable.from(list)
                        .map(this::handlePhotoItem)
                        .toList()
                        .doOnError(e -> setError())
                        .compose(CommonUtil.getCommonTransformer())
                        .subscribe(result -> loadable.setLoading(false))
        );
    }

    @NonNull
    private Boolean handlePhotoItem(PhotoItem photoItem) {
        DownloadInfo downloadInfo = DownloadInfoUtil.getPhotoInfo(photoItem, false);
        File file = FileUtil.getFileFromCode(photoItem.getName(), downloadInfo);

        if (file.exists()) {
            setItemPath(photoItem, downloadInfo);
            return true;
        }

        DownloadUtil.downloadBytes(
                activity,
                loadable,
                getDownloadable(photoItem, downloadInfo),
                photoItem.getName(),
                downloadInfo
        );

        return false;
    }

    private Downloadable getDownloadable(PhotoItem item, DownloadInfo downloadInfo) {
        String code = item.getName();

        return new Downloadable() {
            @Override
            public void downloadSuccess(Object file, @EnumConstant.DownloadType String downloadType) {
                if (file instanceof byte[]) {
                    FileUtil.saveBytesToDevice((byte[]) file, code, downloadInfo);
                    setItemPath(item, downloadInfo);

                }
            }

            @Override
            public void downloadFail(String message) {
                setError();
            }
        };
    }

    private void setItemPath(PhotoItem item, DownloadInfo downloadInfo) {
        subscription.add(
                Observable.just(FileUtil.getFileFromCode(item.getName(), downloadInfo).getPath())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(item::setPath)
        );
    }

    private boolean setError() {
        loadable.setLoading(false);
        loadable.setError(ResourceUtil.getString(R.string.photo_load_error_multiple));

        return false;
    }
}
