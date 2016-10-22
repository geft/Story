package com.mager.story.core;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Gerry on 23/09/2016.
 */

public abstract class CorePresenter<VM extends CoreViewModel> {

    protected CompositeSubscription subscription;
    private VM viewModel;

    public CorePresenter(VM viewModel) {
        this.viewModel = viewModel;
    }

    protected VM getViewModel() {
        return viewModel;
    }

    void setSubscription(CompositeSubscription subscription) {
        this.subscription = subscription;
    }

    protected void showError(Throwable throwable) {

    }
}
