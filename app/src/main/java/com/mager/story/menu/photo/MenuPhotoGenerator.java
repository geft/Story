package com.mager.story.menu.photo;

import android.support.annotation.DrawableRes;

import com.mager.story.R;
import com.mager.story.constant.EnumConstant.PhotoGroup;
import com.mager.story.constant.MapConstant;
import com.mager.story.util.ResourceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gerry on 21/10/2016.
 */

public class MenuPhotoGenerator {

    public List<MenuPhoto> getPhotoList() {
        List<MenuPhoto> list = new ArrayList<>();

        list.add(getMenuPhoto(PhotoGroup.ONS, R.drawable.album_ons));
        list.add(getMenuPhoto(PhotoGroup.AIRY, R.drawable.album_airy));
        list.add(getMenuPhoto(PhotoGroup.SWISSBEL, R.drawable.album_swissbel));

        return list;
    }

    private MenuPhoto getMenuPhoto(@PhotoGroup String photoGroup, @DrawableRes int imageRes) {
        MenuPhoto item = new MenuPhoto();
        item.setImage(ResourceUtil.getDrawable(imageRes));
        item.setPhotoGroup(photoGroup);
        item.setName(MapConstant.PHOTO_COLLECTION.get(photoGroup));

        return item;
    }
}
