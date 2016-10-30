package com.mager.story.core.callback;

/**
 * Created by Gerry on 25/10/2016.
 */

public interface Loadable {
    boolean isLoading();

    void setLoading(boolean loading);

    void setError(String message);
}
