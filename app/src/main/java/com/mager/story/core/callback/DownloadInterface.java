package com.mager.story.core.callback;

import android.support.annotation.Nullable;

import com.mager.story.constant.EnumConstant;

/**
 * Created by Gerry on 29/10/2016.
 */

public interface DownloadInterface {
    void downloadSuccess(@Nullable Object file, @EnumConstant.DownloadType String downloadType);

    void downloadFail(String message);
}
