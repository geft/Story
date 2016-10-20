package com.mager.story.constant;

import android.util.SparseArray;

import com.mager.story.constant.EnumConstant.MenuType;
import com.mager.story.constant.EnumConstant.PhotoGroup;

import java.util.HashMap;

/**
 * Created by Gerry on 21/10/2016.
 */

public class MapConstant {
    public static final SparseArray<String> MENU_GROUP = new SparseArray<>();
    public static final HashMap<String, String> PHOTO_ALBUM = new HashMap<>();

    static {
        MENU_GROUP.put(0, MenuType.PHOTO);
        MENU_GROUP.put(1, MenuType.STORY);
        MENU_GROUP.put(2, MenuType.AUDIO);
    }

    static {
        PHOTO_ALBUM.put(PhotoGroup.ONS, "Owari no Seraph");
        PHOTO_ALBUM.put(PhotoGroup.AIRY, "Airy Slipi");
        PHOTO_ALBUM.put(PhotoGroup.SWISSBEL, "Swiss-Belhotel Jambi");
    }
}
