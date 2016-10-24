package com.mager.story.util;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mager.story.R;
import com.mager.story.constant.EnumConstant.MenuType;
import com.mager.story.login.LoginFragment;

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

    public void signIn(LoginFragment loginFragment, String email, String password) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            handleSignInSuccess(loginFragment, user);
                        } else {
                            handleSignInFailure(loginFragment, task);
                        }
                    } else {
                        handleSignInFailure(loginFragment, task);
                    }
                });
    }

    private void handleSignInSuccess(LoginFragment loginFragment, FirebaseUser user) {
        Log.d(TAG, loginFragment.getString(R.string.auth_signed_in_format, user.getEmail()));
        loginFragment.sendResult(true);
    }

    private void handleSignInFailure(LoginFragment loginFragment, Task<AuthResult> task) {
        Log.w(TAG, "signInWithEmail:failed", task.getException());
        loginFragment.sendResult(false);
    }

    public StorageReference getStorage(@MenuType String menuType) {
        return storage.child(menuType);
    }

    public DatabaseReference getDatabase(@MenuType String menuType) {
        return database.child(menuType);
    }
}
