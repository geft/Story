package com.mager.story.util;

import android.app.Activity;
import android.content.Context;
import android.databinding.ObservableList;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mager.story.Henson;
import com.mager.story.R;
import com.mager.story.constant.EnumConstant.FolderType;
import com.mager.story.home.HomeViewModel;
import com.mager.story.photo.PhotoItem;

import java.util.List;

/**
 * Created by Gerry on 25/09/2016.
 */

public class FirebaseUtil {
    private static final String TAG = "Firebase";

    private FirebaseAuth auth;
    private StorageReference storage;
    private DatabaseReference database;

    public FirebaseUtil() {
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance().getReference();
        database = FirebaseDatabase.getInstance().getReference();
    }

    public static FirebaseAuth.AuthStateListener getFirebaseAuthListener() {
        return firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
            } else {
                Log.d(TAG, "onAuthStateChanged:signed_out");
            }
        };
    }

    public void signIn(Context context, HomeViewModel viewModel) {
        viewModel.setLoading(true);

        FirebaseAuth.getInstance().signInWithEmailAndPassword(viewModel.getEmail(), viewModel.getPassword())
                .addOnCompleteListener(task -> {
                    Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            handleSignInSuccess(context, viewModel, user);
                        } else {
                            handleSignInFailure(context, viewModel, task);
                        }
                    } else {
                        handleSignInFailure(context, viewModel, task);
                    }
                });
    }

    private void handleSignInSuccess(Context context, HomeViewModel viewModel, FirebaseUser user) {
        Log.d(TAG, context.getString(R.string.auth_signed_in_format, user.getEmail()));
        ResourceUtil.showToast(context, context.getString(R.string.auth_sign_in_success));
        viewModel.setLoading(false);

        context.startActivity(
                Henson.with(context)
                        .gotoMenuActivity()
                        .build()
        );
    }

    private void handleSignInFailure(Context context, HomeViewModel viewModel, Task<AuthResult> task) {
        Log.w(TAG, "signInWithEmail:failed", task.getException());
        ResourceUtil.showToast(context, context.getString(R.string.auth_sign_in_fail));

        viewModel.setLoading(false);
    }

    public void populatePhotos(Activity activity, List<PhotoItem> list) {
        generateListItems(activity, list, FolderType.PHOTO);
    }

    @SuppressWarnings("unchecked")
    private void generatePhotos(Activity activity, List list, StorageReference storageRef, int count) {
        for (int i = 0; i < 1; i++) {
            String prefix = (i < 9) ? "0" : "";
            String suffix = ".jpg";

            PhotoItem item = new PhotoItem();
            item.setName(prefix + Integer.toString(i + 1) + suffix);
            list.add(item);
        }

        downloadPhotos(activity, list, storageRef);
    }

    private void downloadPhotos(Activity activity, List<PhotoItem> list, StorageReference storageRef) {
        for (PhotoItem item : list) {
            String name = item.getName();

            if (name != null) {
                storageRef.child(name).getDownloadUrl()
                        .addOnCompleteListener(activity, task -> {
                            if (task.isSuccessful()) {
                                item.setUrl(task.getResult().toString());
                            } else {
                                Log.d(TAG, "Failed to download " + task.getResult().toString());
                            }
                        });
            }
        }
    }

    public void populateStories(Activity activity, ObservableList list) {
        generateListItems(activity, list, FolderType.STORY);
    }

    @SuppressWarnings("unchecked")
    private void generateStories(Activity activity, List list, StorageReference storageRef, int count) {
        for (int i = 0; i < count; i++) {

        }
    }

    public void populateAudios(Activity activity, ObservableList list) {
        generateListItems(activity, list, FolderType.AUDIO);
    }

    @SuppressWarnings("unchecked")
    private void generateAudios(Activity activity, List list, StorageReference storageRef, int count) {
        for (int i = 0; i < count; i++) {

        }
    }

    private void generateListItems(Activity activity, List list, @FolderType String folderType) {
        list.clear();

        StorageReference storageRef = storage.child(folderType);
        DatabaseReference databaseRef = database.child(folderType);
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count = dataSnapshot.getValue(Integer.class);

                switch (folderType) {
                    case FolderType.STORY:
                        generateStories(activity, list, storageRef, count);
                    case FolderType.PHOTO:
                        generatePhotos(activity, list, storageRef, count);
                    case FolderType.AUDIO:
                        generateAudios(activity, list, storageRef, count);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
