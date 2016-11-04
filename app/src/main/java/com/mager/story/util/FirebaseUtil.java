package com.mager.story.util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mager.story.R;
import com.mager.story.constant.EnumConstant.Tag;
import com.mager.story.core.callback.LoginInterface;

import java.util.List;

/**
 * Created by Gerry on 25/09/2016.
 */

public class FirebaseUtil {
    private FirebaseAuth auth;
    private StorageReference storage;

    public FirebaseUtil() {
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance().getReference();
    }

    public FirebaseUtil(StorageReference storage) {
        this.auth = FirebaseAuth.getInstance();
        this.storage = storage;

        resumeDownloads();
    }

    public static FirebaseAuth.AuthStateListener getFirebaseAuthListener() {
        return firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                CrashUtil.logInfo(Tag.LOGIN, ResourceUtil.getString(R.string.firebase_logged_in, user.getEmail()));
            } else {
                CrashUtil.logInfo(Tag.LOGIN, ResourceUtil.getString(R.string.firebase_logged_out));
            }
        };
    }

    private void resumeDownloads() {
        List<FileDownloadTask> tasks = storage.getActiveDownloadTasks();

        try {
            for (FileDownloadTask task : tasks) {
                task
                        .addOnSuccessListener(successTask -> {
                        })
                        .addOnFailureListener(e -> CrashUtil.logWarning(Tag.MENU, e.getMessage()));
            }
        } catch (Exception e) {
            CrashUtil.logError(Tag.MENU, ResourceUtil.getString(R.string.login_download_error), e);
        }
    }

    public void signIn(LoginInterface loginInterface, String email, String password) {
        try {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            if (user != null) {
                                handleSignInSuccess(loginInterface, user);
                            } else {
                                handleSignInFailure(loginInterface);
                            }
                        } else {
                            handleSignInFailure(loginInterface);
                        }
                    });
        } catch (Exception e) {
            CrashUtil.logWarning(Tag.LOGIN, e.getMessage());
        }
    }

    private void handleSignInSuccess(LoginInterface loginInterface, FirebaseUser user) {
        CrashUtil.logInfo(Tag.LOGIN, ResourceUtil.getString(R.string.auth_signed_in_format, user.getEmail()));
        loginInterface.sendSignInResult(true);
    }

    private void handleSignInFailure(LoginInterface loginInterface) {
        CrashUtil.logWarning(Tag.LOGIN, ResourceUtil.getString(
                R.string.auth_sign_in_fail_format,
                loginInterface.getEmail(),
                loginInterface.getPassword(),
                Integer.toString(loginInterface.getCount())));
        loginInterface.sendSignInResult(false);
    }

    public StorageReference getStorageWithChild(String folder) {
        return storage.child(folder);
    }
}
