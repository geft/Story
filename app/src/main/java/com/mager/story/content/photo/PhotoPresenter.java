package com.mager.story.content.photo;

import com.mager.story.core.CorePresenter;
import com.mager.story.menu.photo.MenuPhoto;

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

    public void setBlocking(boolean isBlocking) {
        getViewModel().setBlocking(isBlocking);
    }

    public void setLoading(boolean loading) {
        getViewModel().setLoading(loading);
    }

    public void initViewModel(MenuPhoto menuPhoto) {
        getViewModel().setCode(menuPhoto.getCode());
        getViewModel().setCount(menuPhoto.getCount());
        getViewModel().setName(menuPhoto.getName());
    }
}
