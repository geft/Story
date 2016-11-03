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
        getViewModel().isNightMode.set(!getViewModel().isNightMode.get());
    }

    void setContent(String content) {
        getViewModel().setContent(content);
        getViewModel().ready.set(true);
    }

    void setLoading(boolean loading) {
        getViewModel().ready.set(!loading);
    }

    public void setCode(String code) {
        getViewModel().setCode(code);
    }
}
