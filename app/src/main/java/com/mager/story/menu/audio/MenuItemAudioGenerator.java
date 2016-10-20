package com.mager.story.menu.audio;

import android.content.Context;

import com.mager.story.util.FirebaseUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gerry on 21/10/2016.
 */

public class MenuItemAudioGenerator {

    private String TAG = this.getClass().getName();

    private Context context;
    private FirebaseUtil firebaseUtil;

    public MenuItemAudioGenerator(Context context) {
        this.context = context;
        this.firebaseUtil = new FirebaseUtil();
    }

    public List<MenuItemAudio> getAudioList() {
        return new ArrayList<>();
    }
}
