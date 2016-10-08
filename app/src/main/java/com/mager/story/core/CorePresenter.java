package com.mager.story.core;

import android.content.Context;

/**
 * Created by Gerry on 23/09/2016.
 */

public abstract class CorePresenter<VM extends CoreViewModel> {

    private Context context;
    private VM viewModel;

    public CorePresenter(VM viewModel) {
        this.viewModel = viewModel;
    }

    protected VM getViewModel() {
        return viewModel;
    }

    protected Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    protected String getString(int stringId) {
        return context.getString(stringId);
    }
}
