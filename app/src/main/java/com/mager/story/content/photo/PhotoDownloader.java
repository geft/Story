package com.mager.story.content.photo;

import com.google.firebase.storage.StorageReference;
import com.mager.story.R;
import com.mager.story.constant.EnumConstant.FolderType;
import com.mager.story.core.callback.Loadable;
import com.mager.story.util.CommonUtil;
import com.mager.story.util.FirebaseUtil;
import com.mager.story.util.ResourceUtil;

import java.util.List;

import rx.Observable;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Gerry on 11/10/2016.
 */

class PhotoDownloader {
    private PhotoActivity activity;
    private StorageReference storage;
    private Loadable loadable;
    private CompositeSubscription subscription;

    PhotoDownloader(PhotoActivity activity, CompositeSubscription subscription) {
        this.activity = activity;
        this.loadable = activity;
        this.subscription = subscription;

        FirebaseUtil firebaseUtil = new FirebaseUtil();
        storage = firebaseUtil.getStorageWithChild(FolderType.PHOTO);
    }

    void populatePhotos(List<PhotoItem> photoItems) {
        storage = storage.child(photoItems.get(0).getGroup());
        downloadPhotos(photoItems);
    }

    private void downloadPhotos(List<PhotoItem> list) {
        subscription.add(
                Observable.from(list)
                        .map(photoItem -> storage.child(photoItem.getName()).getDownloadUrl()
                                .addOnCompleteListener(activity, task -> {
                                    if (task.isSuccessful()) {
                                        photoItem.setUrl(task.getResult().toString());
                                    } else {
                                        setError();
                                    }
                                }))
                        .doOnError(throwable -> setError())
                        .compose(CommonUtil.getCommonTransformer())
                        .subscribe()
        );

        loadable.setLoading(false);
    }

    private boolean setError() {
        loadable.setLoading(false);
        loadable.setError(ResourceUtil.getString(R.string.photo_load_error_multiple));

        return true;
    }
}
