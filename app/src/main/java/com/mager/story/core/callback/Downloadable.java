package com.mager.story.core.callback;

import com.mager.story.constant.EnumConstant;

/**
 * Created by Gerry on 29/10/2016.
 */

public interface Downloadable {
    void downloadSuccess(Object file, @EnumConstant.DownloadType String downloadType);

    void downloadFail(String message);
}
