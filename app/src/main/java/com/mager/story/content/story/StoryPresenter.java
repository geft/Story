package com.mager.story.content.story;

import com.mager.story.core.CorePresenter;

/**
 * Created by Gerry on 22/10/2016.
 */

class StoryPresenter extends CorePresenter<StoryViewModel> {

    StoryPresenter(StoryViewModel viewModel) {
        super(viewModel);
    }

    void setTitle(String title, String chapter) {
        getViewModel().setTitle(title);
        getViewModel().setChapter(chapter);
    }

    void toggleNightMode() {
        getViewModel().setNightMode(!getViewModel().isNightMode());
    }

    void setContent(String content) {
        getViewModel().setContent(content);
        getViewModel().setReady(true);
    }

    void setLoading(boolean loading) {
        getViewModel().setReady(!loading);
    }
}
