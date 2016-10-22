package com.mager.story.content.story;

import com.mager.story.constant.EnumConstant;

import org.parceler.Parcel;

/**
 * Created by Gerry on 22/10/2016.
 */

@Parcel
public class StoryParcel {

    @EnumConstant.StoryChapter
    public String chapter;

    @EnumConstant.StoryTitle
    public String title;
}
