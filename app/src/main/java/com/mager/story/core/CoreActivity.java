package com.mager.story.core;

import android.app.Activity;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import com.f2prateek.dart.Dart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mager.story.R;
import com.squareup.leakcanary.LeakCanary;

import org.parceler.Parcels;

/**
 * Created by Gerry on 23/09/2016.
 */

public abstract class CoreActivity<P extends CorePresenter, VM extends CoreViewModel>
        extends Activity {

    private static final String TAG = "AUTH";
    private static final String PARCEL = "PARCEL";

    private P presenter;
    private VM viewModel;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initLeakCanary();
        initDart();
        initAuth();

        presenter = createPresenter();
        viewModel = getViewModel();
        initBinding(viewModel);
    }

    @Override
    protected void onStart() {
        super.onStart();

        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    private void initAuth() {
        firebaseAuth = FirebaseAuth.getInstance();

        authStateListener = firebaseAuth1 -> {
            FirebaseUser user = firebaseAuth1.getCurrentUser();

            if (user == null) {
                Log.d(TAG, getString(R.string.auth_signed_out));
            } else {
                Log.d(TAG, getString(
                        R.string.auth_signed_in,
                        user.getDisplayName(),
                        user.getUid(),
                        user.getEmail()));
            }
        };
    }

    private void initDart() {
        Dart.inject(this);
    }

    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this.getApplication());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Parcelable parcelable = Parcels.wrap(getViewModel().getClass(), getViewModel());
        outState.putParcelable(PARCEL, parcelable);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        viewModel = Parcels.unwrap(savedInstanceState.getParcelable(PARCEL));
    }

    protected abstract VM getViewModel();

    protected abstract P createPresenter();

    protected P getPresenter() {
        return presenter;
    }

    protected abstract ViewDataBinding initBinding(VM viewModel);
}
