package com.mager.story.core;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;

import com.f2prateek.dart.Dart;
import com.google.firebase.auth.FirebaseAuth;
import com.mager.story.util.FirebaseHelper;
import com.squareup.leakcanary.LeakCanary;

import org.parceler.Parcels;

/**
 * Created by Gerry on 23/09/2016.
 */

public abstract class CoreActivity<P extends CorePresenter, VM extends CoreViewModel>
        extends FragmentActivity {

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
        initFirebase();

        presenter = createPresenter();
        viewModel = createViewModel();

        initBinding(viewModel);
    }

    private void initFirebase() {
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = FirebaseHelper.getFirebaseAuthListener();
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
        Parcelable parcelable = Parcels.wrap(viewModel.getClass(), viewModel);
        outState.putParcelable(PARCEL, parcelable);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {
            viewModel = Parcels.unwrap(savedInstanceState.getParcelable(PARCEL));
        }
    }

    protected abstract VM createViewModel();

    protected abstract P createPresenter();

    protected abstract ViewDataBinding initBinding(VM viewModel);

    protected P getPresenter() {
        return presenter;
    }

    protected VM getViewModel() {
        return viewModel;
    }
}
