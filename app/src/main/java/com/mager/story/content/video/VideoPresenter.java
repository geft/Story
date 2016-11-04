package com.mager.story.content.video;

import com.mager.story.core.CorePresenter;

/**
 * Created by Gerry on 29/10/2016.
 */

public class VideoPresenter extends CorePresenter<VideoViewModel> {

    VideoPresenter(VideoViewModel viewModel) {
        super(viewModel);
    }

    void setLoading(boolean loading) {
        getViewModel().setLoading(loading);
    }

    void setReady(boolean ready) {
        getViewModel().setReady(ready);
    }
}
