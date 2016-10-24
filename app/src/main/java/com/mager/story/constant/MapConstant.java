package com.mager.story.constant;

import com.mager.story.constant.EnumConstant.PhotoGroup;
import com.mager.story.constant.EnumConstant.StoryChapter;
import com.mager.story.constant.EnumConstant.StoryTitle;

import java.util.HashMap;

/**
 * Created by Gerry on 21/10/2016.
 */

public class MapConstant {
    public static final HashMap<String, String> PHOTO_COLLECTION = new HashMap<>();
    public static final HashMap<String, String> STORY_COLLECTION = new HashMap<>();

    static {
        PHOTO_COLLECTION.put(PhotoGroup.ONS, "Owari no Seraph");
        PHOTO_COLLECTION.put(PhotoGroup.AIRY, "Airy Slipi");
        PHOTO_COLLECTION.put(PhotoGroup.SWISSBEL, "Swiss-Belhotel Jambi");
    }

    static {
        STORY_COLLECTION.put(StoryChapter.CH0, StoryTitle.CH0);
        STORY_COLLECTION.put(StoryChapter.CH1, StoryTitle.CH1);
        STORY_COLLECTION.put(StoryChapter.CH2, StoryTitle.CH2);
        STORY_COLLECTION.put(StoryChapter.CH3, StoryTitle.CH3);
        STORY_COLLECTION.put(StoryChapter.CH4, StoryTitle.CH4);
    }
}
