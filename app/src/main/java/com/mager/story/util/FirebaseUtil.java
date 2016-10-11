package com.mager.story.util;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mager.story.Henson;
import com.mager.story.R;
import com.mager.story.constant.EnumConstant.FolderType;
import com.mager.story.home.HomeViewModel;

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

    public StorageReference getStorage(@FolderType String folderType) {
        return storage.child(folderType);
    }

    public DatabaseReference getDatabase(@FolderType String folderType) {
        return database.child(folderType);
    }
}
