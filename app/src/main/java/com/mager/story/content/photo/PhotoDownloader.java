package com.mager.story.content.photo;

import android.app.Activity;

import com.mager.story.R;
import com.mager.story.core.callback.Loadable;
import com.mager.story.data.DownloadInfoUtil;
import com.mager.story.util.CommonUtil;
import com.mager.story.util.DownloadUtil;
import com.mager.story.util.ResourceUtil;

import java.util.List;

import rx.Observable;
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

    void setUrls(List<PhotoItem> list) {
        subscription.add(
                Observable.from(list)
                        .map(photoItem -> {
                            DownloadUtil.downloadUriIntoPhotoItem(
                                    activity, loadable,
                                    DownloadInfoUtil.getPhotoInfo(photoItem, false),
                                    photoItem);

                            return true;
                        })
                        .toList()
                        .doOnError(e -> setError())
                        .compose(CommonUtil.getCommonTransformer())
                        .subscribe(result -> loadable.setLoading(false))
        );
    }

    private boolean setError() {
        loadable.setLoading(false);
        loadable.setError(ResourceUtil.getString(R.string.photo_load_error_multiple));

        return false;
    }
}
