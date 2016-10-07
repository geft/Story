package com.mager.story.util;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.mager.story.R;
import com.mager.story.menu.Henson;

/**
 * Created by Gerry on 25/09/2016.
 */

public class FirebaseUtil {
    private static final String TAG = "Firebase";
    private static final String reversedHash = "03d6e83e5956797cee0b02370cb33d5f";
    private static final String clientId = "515396136492-92r0i6s7nnqu7u71o4v2bmg6cmbr0ur1.apps.googleusercontent.com";

    private GoogleApiClient googleApiClient;
    private FragmentActivity activity;

    public FirebaseUtil(FragmentActivity activity, String key) {
        this.activity = activity;

        GoogleSignInOptions gso = getSignInOptions(key);
        googleApiClient = getGoogleAPIClient(activity, gso);
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

    private String getClientId(String key) {
        if (isPasswordValid(key)) {
            return clientId;
        } else {
            return "null";
        }
    }

    private boolean isPasswordValid(String key) {
        String encryptedInput = new StringBuilder(EncryptionUtil.MD5(key)).reverse().toString();
        return encryptedInput.equals(reversedHash);
    }

    @NonNull
    private GoogleApiClient getGoogleAPIClient(FragmentActivity activity, GoogleSignInOptions gso) {
        return new GoogleApiClient.Builder(activity)
                .enableAutoManage(activity, connectionResult -> ResourceUtil.showToast(
                        activity, activity.getString(R.string.auth_connection_fail)))
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private GoogleSignInOptions getSignInOptions(String key) {
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getClientId(key))
                .requestEmail()
                .build();
    }

    public Intent getSignInIntent() {
        return Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
    }

    @Nullable
    public GoogleSignInAccount handleSignInResult(Intent data) {
        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());

        if (result.isSuccess()) {
            return result.getSignInAccount();
        } else {
            return null;
        }
    }

    public void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(activity, task -> {
                    Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                    if (task.isSuccessful()) {
                        ResourceUtil.showToast(activity, activity.getString(R.string.auth_sign_in_success));
                        Log.d(TAG, activity.getString(R.string.auth_signed_in, account.getDisplayName(), account.getEmail()));
                        activity.startActivity(Henson.with(activity).gotoMenuActivity().build());
                    } else {
                        Log.d(TAG, "signInWithCredential", task.getException());
                        ResourceUtil.showToast(activity, activity.getString(R.string.auth_sign_in_fail));
                    }
                });
    }
}
