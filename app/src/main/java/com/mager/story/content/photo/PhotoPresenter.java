package com.mager.story.content.photo;

import com.mager.story.core.CorePresenter;
import com.mager.story.menu.photo.MenuPhoto;
import com.mager.story.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gerry on 08/10/2016.
 */

class PhotoPresenter extends CorePresenter<PhotoViewModel> {

    PhotoPresenter(PhotoViewModel viewModel) {
        super(viewModel);
    }

    public void setBlocking(boolean isBlocking) {
        getViewModel().blocking.set(isBlocking);
    }

    public void setLoading(boolean loading) {
        getViewModel().loading.set(loading);
    }

    public void initViewModel(MenuPhoto menuPhoto) {
        getViewModel().setCode(menuPhoto.getCode());
        getViewModel().count.set(menuPhoto.getCount());
        getViewModel().setName(menuPhoto.getName());
        getViewModel().setItems(getPhotoList(menuPhoto.getCode(), menuPhoto.getCount()));
    }

    private List<PhotoItem> getPhotoList(String group, int count) {
        List<PhotoItem> list = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            PhotoItem item = new PhotoItem();
            item.setName(StringUtil.padLeft("0", Integer.toString(i + 1), 2));
            item.setGroup(group);
            list.add(item);
        }

        return list;
    }
}
