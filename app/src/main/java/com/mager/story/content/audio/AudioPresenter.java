package com.mager.story.content.audio;

import com.mager.story.core.CorePresenter;

/**
 * Created by Gerry on 04/11/2016.
 */

public class AudioPresenter extends CorePresenter<AudioViewModel> {

    AudioPresenter(AudioViewModel viewModel) {
        super(viewModel);
    }

    void setLoading(boolean loading) {
        getViewModel().loading.set(loading);
    }

    void showError(boolean show) {
        getViewModel().showError.set(show);
    }

    public void setPaused(boolean isPaused) {
        getViewModel().paused.set(isPaused);
    }
}
