package com.mager.story.error;

import com.mager.story.core.CorePresenter;

/**
 * Created by Gerry on 27/10/2016.
 */

class ErrorPresenter extends CorePresenter<ErrorViewModel> {

    ErrorPresenter(ErrorViewModel viewModel) {
        super(viewModel);
    }

    public void setMessage(String message) {
        getViewModel().setMessage(message);
    }
}
