package com.mager.story.core;

/**
 * Created by Gerry on 23/09/2016.
 */

public abstract class CorePresenter<VM extends CoreViewModel> {

    private VM viewModel;

    public CorePresenter(VM viewModel) {
        this.viewModel = viewModel;
    }

    protected VM getViewModel() {
        return viewModel;
    }
}
