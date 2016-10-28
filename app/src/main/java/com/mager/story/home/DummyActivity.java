package com.mager.story.home;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.f2prateek.dart.HensonNavigable;

/**
 * Created by Gerry on 29/10/2016.
 */

@HensonNavigable
public class DummyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(this::finish, 200);
    }
}
