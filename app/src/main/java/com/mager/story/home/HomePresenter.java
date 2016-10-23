package com.mager.story.home;

import com.mager.story.core.CorePresenter;

/**
 * Created by Gerry on 24/10/2016.
 */

public class HomePresenter extends CorePresenter<HomeViewModel> {
    @Override
    protected HomeViewModel getViewModel() {
        return new HomeViewModel();
    }
}
