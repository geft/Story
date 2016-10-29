package com.mager.story.core;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.f2prateek.dart.Dart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.mager.story.util.FirebaseUtil;
import com.squareup.leakcanary.LeakCanary;

import org.parceler.ParcelerRuntimeException;
import org.parceler.Parcels;

import rx.subscriptions.CompositeSubscription;

import static android.view.WindowManager.LayoutParams.FLAG_SECURE;

/**
 * Created by Gerry on 23/09/2016.
 */

public abstract class CoreActivity<P extends CorePresenter, VM extends CoreViewModel>
        extends AppCompatActivity {

    private static final String TAG = "AUTH";
    private static final String PARCEL = "PARCEL";
    private static final String STORAGE = "STORAGE";
    protected CompositeSubscription subscription;
    private P presenter;
    private VM viewModel;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initWindowStyle();
        initLeakCanary();
        initFirebaseAuth();
        initDart();

        subscription = new CompositeSubscription();

        if (savedInstanceState != null) {
            viewModel = Parcels.unwrap(savedInstanceState.getParcelable(PARCEL));
        } else {
            viewModel = createViewModel();
        }

        presenter = createPresenter(viewModel);
        presenter.setSubscription(subscription);

        initBinding(viewModel);
    }

    private void initDart() {
        Dart.inject(this);
    }

    private void initWindowStyle() {
        getWindow().setFlags(FLAG_SECURE, FLAG_SECURE);
    }

    private void initFirebaseAuth() {
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = FirebaseUtil.getFirebaseAuthListener();
    }

    @Override
    protected void onStart() {
        super.onStart();

        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onDestroy() {
        subscription.unsubscribe();

        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }

        super.onDestroy();
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
        try {
            outState.putString(STORAGE, FirebaseStorage.getInstance().toString());
            outState.putParcelable(PARCEL, Parcels.wrap(viewModel));
        } catch (ParcelerRuntimeException e) {
            Log.w(TAG, "Unable to parcel " + viewModel.getClass().getCanonicalName());
        }

        super.onSaveInstanceState(outState);
    }

    protected abstract VM createViewModel();

    protected abstract P createPresenter(VM viewModel);

    protected abstract ViewDataBinding initBinding(VM viewModel);

    public P getPresenter() {
        return presenter;
    }

    public VM getViewModel() {
        return viewModel;
    }
}
