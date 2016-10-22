package com.mager.story.util;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Gerry on 22/10/2016.
 */

public class CommonUtil {

    public static <T> Observable.Transformer<T, T> getCommonTransformer() {
        return observable -> observable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
