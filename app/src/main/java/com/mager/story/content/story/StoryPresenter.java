package com.mager.story.content.story;

import com.mager.story.core.CorePresenter;

import java.nio.charset.Charset;

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

    void setLoading(boolean loading) {
        getViewModel().ready.set(!loading);
    }

    public void setCode(String code) {
        getViewModel().setCode(code);
    }

    public void setContent(byte[] bytes) {
        String content = new String(bytes, Charset.defaultCharset());
        getViewModel().setContent(content);
        getViewModel().ready.set(true);
    }
}
