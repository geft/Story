package com.mager.story.content.photo;

import android.app.Dialog;

import com.mager.story.core.CorePresenter;

import java.util.List;

/**
 * Created by Gerry on 08/10/2016.
 */

class PhotoPresenter extends CorePresenter<PhotoViewModel> {

    PhotoPresenter(PhotoViewModel viewModel) {
        super(viewModel);
    }

    void handleItemClick(PhotoItem item, String url) {
        Dialog dialog = new PhotoDialog(getContext(), url);
        dialog.show();
    }

    void setItems(List<PhotoItem> list) {
        getViewModel().setItems(list);
    }
}
