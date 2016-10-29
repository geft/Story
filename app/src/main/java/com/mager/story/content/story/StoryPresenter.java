package com.mager.story.content.story;

import com.mager.story.core.CorePresenter;
import com.mager.story.home.LoadingInterface;

/**
 * Created by Gerry on 22/10/2016.
 */

class StoryPresenter extends CorePresenter<StoryViewModel> {

    StoryPresenter(StoryViewModel viewModel) {
        super(viewModel);
    }

    void populateData(String title, String chapter, LoadingInterface loadingInterface) {
        getViewModel().setTitle(title);
        getViewModel().setChapter(chapter);
        loadingInterface.setLoading(true);
    }

    public void toggleNightMode() {
        getViewModel().setNightMode(!getViewModel().isNightMode());
    }
}
