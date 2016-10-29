package com.mager.story.content.photo;

import com.google.firebase.storage.StorageReference;
import com.mager.story.R;
import com.mager.story.constant.EnumConstant.FolderType;
import com.mager.story.home.LoadingInterface;
import com.mager.story.util.CommonUtil;
import com.mager.story.util.FirebaseUtil;
import com.mager.story.util.ResourceUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.subscriptions.CompositeSubscription;

import static com.mager.story.constant.EnumConstant.PhotoType;

/**
 * Created by Gerry on 11/10/2016.
 */

class PhotoDownloader {
    private PhotoFragment fragment;
    private StorageReference storage;
    private LoadingInterface loadingInterface;
    private CompositeSubscription subscription;

    PhotoDownloader(PhotoFragment fragment, LoadingInterface loadingInterface, CompositeSubscription subscription) {
        this.fragment = fragment;
        this.loadingInterface = loadingInterface;
        this.subscription = subscription;

        FirebaseUtil firebaseUtil = new FirebaseUtil();
        storage = firebaseUtil.getStorageWithChild(FolderType.PHOTO);
    }

    void populatePhotos(String group, int count) {
        storage = storage.child(group);
        generatePhotos(group, count);
    }

    private void generatePhotos(String group, int count) {
        List<PhotoItem> list = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            String type = PhotoType.THUMB;
            String prefix = (i < 9) ? "0" : "";
            String extension = ".jpg";

            PhotoItem item = new PhotoItem();
            item.setName(type + prefix + Integer.toString(i + 1) + extension);
            item.setGroup(group);
            list.add(item);
        }

        downloadPhotos(list);
    }

    private void downloadPhotos(List<PhotoItem> list) {
        subscription.add(
                Observable.from(list)
                        .map(photoItem -> storage.child(photoItem.getName()).getDownloadUrl()
                                .addOnCompleteListener(fragment.getActivity(), task -> {
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

        fragment.setItems(list);
        loadingInterface.setLoading(false);
    }

    private void setError() {
        loadingInterface.setLoading(false);
        loadingInterface.setError(ResourceUtil.getString(R.string.photo_load_error_multiple));
    }
}
