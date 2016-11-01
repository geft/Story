package com.mager.story.core.callback;

/**
 * Created by Gerry on 24/10/2016.
 */

public interface LoginInterface {

    void sendSignInResult(boolean isSuccess);

    String getEmail();

    String getPassword();

    int getCount();
}
