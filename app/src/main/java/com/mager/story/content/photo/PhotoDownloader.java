package com.mager.story.content.photo;

import android.support.annotation.NonNull;

import com.google.firebase.storage.StorageReference;
import com.mager.story.R;
import com.mager.story.constant.EnumConstant.FolderType;
import com.mager.story.core.callback.Blockable;
import com.mager.story.core.callback.Loadable;
import com.mager.story.util.CommonUtil;
import com.mager.story.util.FirebaseUtil;
import com.mager.story.util.ResourceUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.subscriptions.CompositeSubscription;

import static com.mager.story.constant.EnumConstant.FolderType.PHOTO;
import static com.mager.story.constant.EnumConstant.PhotoType;

/**
 * Created by Gerry on 11/10/2016.
 */

class PhotoDownloader {
    private static final String TAG_DIALOG = "DIALOG";
    private PhotoActivity activity;
    private StorageReference storage;
    private Loadable loadable;
    private Blockable blockable;
    private CompositeSubscription subscription;

    PhotoDownloader(PhotoActivity activity, CompositeSubscription subscription) {
        this.activity = activity;
        this.loadable = activity;
        this.blockable = activity;
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

        activity.setItems(list);
        loadable.setLoading(false);
    }

    private void setError() {
        loadable.setLoading(false);
        loadable.setError(ResourceUtil.getString(R.string.photo_load_error_multiple));
    }

    void openFullPhoto(PhotoItem item) {
        if (blockable.isBlocked()) {
            return;
        } else {
            blockable.setBlock(true);
        }

        PhotoDialog dialog = showDialog();

        StorageReference storage = new FirebaseUtil().getStorageWithChild(PHOTO).child(item.getGroup());
        String fullName = item.getName().replace(PhotoType.THUMB, PhotoType.FULL);

        storage.child(fullName).getDownloadUrl()
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        dialog.loadImage(task.getResult().toString());
                    } else {
                        setError();
                    }
                });
    }

    @NonNull
    private PhotoDialog showDialog() {
        PhotoDialog dialog = new PhotoDialog();
        dialog.setBlockable(activity);
        dialog.show(activity.getFragmentManager(), TAG_DIALOG);
        return dialog;
    }
}
