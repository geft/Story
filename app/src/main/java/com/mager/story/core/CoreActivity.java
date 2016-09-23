package com.mager.story.core;

import android.app.Activity;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Parcelable;

import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;

import org.parceler.Parcel;
import org.parceler.Parcels;

/**
 * Created by Gerry on 23/09/2016.
 */

public abstract class CoreActivity<P extends CorePresenter, VM extends CoreViewModel>
        extends Activity {

    private static final String PARCEL = "PARCEL";

    private VM viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Dart.inject(this);

        viewModel = getViewModel();
        initBinding(viewModel);
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

    protected abstract ViewDataBinding initBinding(VM viewModel);
}
