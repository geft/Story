package com.mager.story.photo;

import android.app.Activity;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.mager.story.constant.EnumConstant.FolderType;
import com.mager.story.util.FirebaseUtil;

import java.util.ArrayList;
import java.util.List;

import static com.mager.story.constant.EnumConstant.PhotoGroup;
import static com.mager.story.constant.EnumConstant.PhotoType;

/**
 * Created by Gerry on 11/10/2016.
 */

class PhotoDownloader {
    private String TAG = this.getClass().getName();

    private Activity activity;
    private DatabaseReference database;
    private StorageReference storage;

    PhotoDownloader(Activity activity) {
        this.activity = activity;

        FirebaseUtil firebaseUtil = new FirebaseUtil();
        database = firebaseUtil.getDatabase(FolderType.PHOTO);
        storage = firebaseUtil.getStorage(FolderType.PHOTO);
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
            }
        });
    }

    private void generatePhotos(int count, @PhotoGroup String group) {
        List<PhotoItem> list = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            String type = PhotoType.THUMB;
            String prefix = (i < 9) ? "0" : "";
            String suffix = ".jpg";

            PhotoItem item = new PhotoItem();
            item.setName(type + prefix + Integer.toString(i + 1) + suffix);
            item.setGroup(group);
            list.add(item);
        }

        downloadPhotos(list);
    }

    private void downloadPhotos(List<PhotoItem> list) {
        for (PhotoItem item : list) {
            String name = item.getName();

            if (name != null) {
                storage.child(item.getName()).getDownloadUrl()
                        .addOnCompleteListener(activity, task -> {
                            if (task.isSuccessful()) {
                                try {
                                    item.setUrl(task.getResult().toString());
                                } catch (Exception e) {
                                    Log.d(TAG, "Failed to download " + name);
                                }
                            }
                        });
            }
        }

        ((PhotoActivity) activity).setItems(list);
    }
}
