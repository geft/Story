package com.mager.story.photo;

import android.databinding.BindingAdapter;
import android.view.View;

import com.mager.story.core.CorePresenter;
import com.mager.story.util.ResourceUtil;

import java.util.List;

/**
 * Created by Gerry on 08/10/2016.
 */

public class PhotoPresenter extends CorePresenter<PhotoViewModel> {

    PhotoPresenter(PhotoViewModel viewModel) {
        super(viewModel);
    }

    void populateData() {
        List<PhotoItem> list = getViewModel().items;

        PhotoItem item = new PhotoItem();
        item.setUrl("https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png");

        for (int i = 0; i < 10; i++) {
            list.add(item);
        }
    }

    void handleItemClick(PhotoItem item, int position) {
        ResourceUtil.showToast(getContext(), Integer.toString(position));
    }
}
