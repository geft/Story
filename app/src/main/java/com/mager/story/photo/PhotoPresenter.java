package com.mager.story.photo;

import com.mager.story.core.CorePresenter;
import com.mager.story.util.ResourceUtil;

/**
 * Created by Gerry on 08/10/2016.
 */

class PhotoPresenter extends CorePresenter<PhotoViewModel> {

    PhotoPresenter(PhotoViewModel viewModel) {
        super(viewModel);
    }

    void handleItemClick(PhotoItem item, int position) {
        ResourceUtil.showToast(getContext(), Integer.toString(position));
    }
}
