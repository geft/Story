package com.mager.story.util;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
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

import java.util.Arrays;

/**
 * Created by Gerry on 25/09/2016.
 */

public class FirebaseHelper {
    public static final int RC_SIGN_IN = 100;
    private static final String TAG = "Firebase";
    private static final String encryptedPassword = "gtpaGltYXdhcmk=";
    private static final String encryptedClientId = "sHbNTE1Mzk2MTM2NDkyLTkycjBpNnM3bm5xdTd1NzFvNHYyYm1nNmNtYnIwdXIxLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29t";

    private GoogleApiClient googleApiClient;
    private FragmentActivity activity;

    public FirebaseHelper(FragmentActivity activity, String password) {
        this.activity = activity;

        GoogleSignInOptions gso = getSignInOptions(password);
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

    /**
     * encryption  Base64
     * password    himawari
     * client id   515396136492-92r0i6s7nnqu7u71o4v2bmg6cmbr0ur1.apps.googleusercontent.com
     */
    private String getClientId(String password) {
        if (isPasswordValid(password)) {
            return Arrays.toString(
                    Base64.decode(getBytesFromEncryptedString(encryptedClientId), Base64.DEFAULT));
        } else {
            return "null";
        }
    }

    private boolean isPasswordValid(String password) {
        return Arrays.equals(
                getBytesFromEncryptedString(encryptedPassword),
                Base64.encode(password.getBytes(), Base64.DEFAULT));
    }

    private byte[] getBytesFromEncryptedString(String key) {
        return key.substring(3).getBytes();
    }

    @NonNull
    private GoogleApiClient getGoogleAPIClient(FragmentActivity activity, GoogleSignInOptions gso) {
        return new GoogleApiClient.Builder(activity)
                .enableAutoManage(activity, connectionResult -> ResourceUtil.showToast(
                        activity, activity.getString(R.string.error_connection_fail)))
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private GoogleSignInOptions getSignInOptions(String password) {
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getClientId(password))
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
                    } else {
                        Log.d(TAG, "signInWithCredential", task.getException());
                        ResourceUtil.showToast(activity, activity.getString(R.string.auth_sign_in_fail));
                    }
                });
    }
}
