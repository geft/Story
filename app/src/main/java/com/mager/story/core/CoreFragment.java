package com.mager.story.core;

import android.app.Fragment;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.util.Log;

import org.parceler.ParcelerRuntimeException;
import org.parceler.Parcels;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Gerry on 23/09/2016.
 */

public abstract class CoreFragment<P extends CorePresenter, VM extends CoreViewModel>
        extends Fragment {

    protected CompositeSubscription subscription;
    private String PARCEL_KEY = "PARCEL_KEY";
    private P presenter;
    private VM viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            viewModel = Parcels.unwrap(savedInstanceState.getParcelable(PARCEL_KEY));
        } else {
            viewModel = createViewModel();
        }

        subscription = new CompositeSubscription();

        presenter = createPresenter(viewModel);
        presenter.setSubscription(subscription);

        initBinding(viewModel);
    }

    @Override
    public void onDestroy() {
        subscription.unsubscribe();
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        try {
            outState.putParcelable(PARCEL_KEY, Parcels.wrap(viewModel));
        } catch (ParcelerRuntimeException e) {
            Log.w(this.getClass().getName(), "Unable to parcel " + viewModel.getClass().getCanonicalName());
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
