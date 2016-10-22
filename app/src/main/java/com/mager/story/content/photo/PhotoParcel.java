package com.mager.story.content.photo;

import com.mager.story.constant.EnumConstant;

import org.parceler.Parcel;

/**
 * Created by Gerry on 20/10/2016.
 */

@Parcel
public class PhotoParcel {

    @EnumConstant.PhotoGroup
    public String photoGroup;
}
