package com.mager.story.core;

import android.content.ComponentCallbacks2;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.f2prateek.dart.Dart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.mager.story.Henson;
import com.mager.story.util.CommonUtil;
import com.mager.story.util.FirebaseUtil;

import org.parceler.ParcelerRuntimeException;
import org.parceler.Parcels;

import rx.subscriptions.CompositeSubscription;

import static android.view.WindowManager.LayoutParams.FLAG_SECURE;

/**
 * Created by Gerry on 23/09/2016.
 */

public abstract class CoreActivity<P extends CorePresenter, VM extends CoreViewModel>
        extends AppCompatActivity
        implements ComponentCallbacks2 {

    private static final String TAG = "AUTH";
    private static final String PARCEL = "PARCEL";
    private static final String STORAGE = "STORAGE";
    protected CompositeSubscription subscription;
    protected FirebaseAuth firebaseAuth;
    protected FirebaseUtil firebaseUtil;
    private P presenter;
    private VM viewModel;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initWindowStyle();
        initFirebaseAuth();
        initDart();

        subscription = new CompositeSubscription();

        if (savedInstanceState != null) {
            initFirebaseUtilWithStorage(savedInstanceState);
            viewModel = Parcels.unwrap(savedInstanceState.getParcelable(PARCEL));
        } else {
            firebaseUtil = new FirebaseUtil();
            viewModel = createViewModel();
        }

        presenter = createPresenter(viewModel);
        presenter.setSubscription(subscription);

        initBinding(viewModel);
    }

    private void initFirebaseUtilWithStorage(Bundle savedInstanceState) {
        String storageReference = savedInstanceState.getString(STORAGE);

        if (storageReference != null) {
            firebaseUtil = new FirebaseUtil(
                    FirebaseStorage.getInstance().getReference(storageReference));
        }
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
    protected void onStop() {
        if (!CommonUtil.isDisplayOn(this)) {
            exitApp();
        }

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        subscription.unsubscribe();

        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }

        super.onDestroy();
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

    @Override
    public void onTrimMemory(int level) {
        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            exitApp();
        }
    }

    private void exitApp() {
        firebaseAuth.signOut();
        startActivity(Henson.with(this).gotoDummyActivity().build());
        finish();
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
