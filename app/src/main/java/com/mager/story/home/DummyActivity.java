package com.mager.story.home;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.f2prateek.dart.HensonNavigable;

/**
 * Created by Gerry on 29/10/2016.
 */

@HensonNavigable
public class DummyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(this::finish, 500);
    }
}
