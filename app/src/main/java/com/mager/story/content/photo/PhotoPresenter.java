package com.mager.story.content.photo;

import com.mager.story.core.CorePresenter;

import java.util.List;

/**
 * Created by Gerry on 08/10/2016.
 */

class PhotoPresenter extends CorePresenter<PhotoViewModel> {

    PhotoPresenter(PhotoViewModel viewModel) {
        super(viewModel);
    }

    void setItems(List<PhotoItem> list) {
        getViewModel().setItems(list);
    }
}
