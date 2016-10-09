package com.mager.story.photo;

import com.mager.story.core.CorePresenter;
import com.mager.story.util.ResourceUtil;

import java.util.List;

/**
 * Created by Gerry on 08/10/2016.
 */

class PhotoPresenter extends CorePresenter<PhotoViewModel> {

    PhotoPresenter(PhotoViewModel viewModel) {
        super(viewModel);
    }

    void populateData() {
        List<PhotoItem> list = getViewModel().items;

        PhotoItem item = new PhotoItem();
        item.setUrl("http://www.freeiconspng.com/uploads/multimedia-photo-icon-31.png");

        for (int i = 0; i < 16; i++) {
            list.add(item);
        }
    }

    void handleItemClick(PhotoItem item, int position) {
        ResourceUtil.showToast(getContext(), Integer.toString(position));
    }
}
