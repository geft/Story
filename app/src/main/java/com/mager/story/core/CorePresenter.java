package com.mager.story.core;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Gerry on 23/09/2016.
 */

public abstract class CorePresenter<VM extends CoreViewModel> {

    protected CompositeSubscription subscription;

    protected abstract VM getViewModel();

    void setSubscription(CompositeSubscription subscription) {
        this.subscription = subscription;
    }
}
