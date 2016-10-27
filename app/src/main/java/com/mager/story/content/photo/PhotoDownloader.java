package com.mager.story.content.photo;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.mager.story.R;
import com.mager.story.constant.EnumConstant.MenuType;
import com.mager.story.home.LoadingInterface;
import com.mager.story.util.FirebaseUtil;
import com.mager.story.util.ResourceUtil;

import java.util.ArrayList;
import java.util.List;

import static com.mager.story.constant.EnumConstant.PhotoGroup;
import static com.mager.story.constant.EnumConstant.PhotoType;

/**
 * Created by Gerry on 11/10/2016.
 */

class PhotoDownloader {
    private static String IMAGE_EXT = "jpg";
    private String TAG = this.getClass().getName();

    private PhotoFragment fragment;
    private DatabaseReference database;
    private StorageReference storage;
    private LoadingInterface loadingInterface;

    PhotoDownloader(PhotoFragment fragment, LoadingInterface loadingInterface) {
        this.fragment = fragment;
        this.loadingInterface = loadingInterface;

        FirebaseUtil firebaseUtil = new FirebaseUtil();
        database = firebaseUtil.getDatabase(MenuType.PHOTO);
        storage = firebaseUtil.getStorage(MenuType.PHOTO);
    }

    void populatePhotos(@PhotoGroup String group) {
        database = database.child(group);
        storage = storage.child(group);

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count = dataSnapshot.getValue(Integer.class);
                generatePhotos(count, group);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, databaseError.getMessage());
                setError();
            }
        });
    }

    private void generatePhotos(int count, @PhotoGroup String group) {
        List<PhotoItem> list = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            String type = PhotoType.THUMB;
            String prefix = (i < 9) ? "0" : "";
            String suffix = IMAGE_EXT;

            PhotoItem item = new PhotoItem();
            item.setName(type + prefix + Integer.toString(i + 1) + suffix);
            item.setGroup(group);
            list.add(item);
        }

        downloadPhotos(list);
    }

    private void downloadPhotos(List<PhotoItem> list) {
        for (PhotoItem item : list) {
            try {
                storage.child(item.getName()).getDownloadUrl()
                        .addOnCompleteListener(fragment.getActivity(), task -> {
                            if (task.isSuccessful()) {
                                item.setUrl(task.getResult().toString());
                            } else {
                                setError();
                            }
                        });
            } catch (Exception e) {
                setError();
            }
        }

        fragment.setItems(list);
        loadingInterface.setLoading(false);
    }

    private void setError() {
        loadingInterface.setError(ResourceUtil.getString(R.string.photo_load_error));
    }
}
