package com.mager.story.home;


import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;

import com.mager.story.R;
import com.mager.story.content.audio.AudioFragmentBuilder;
import com.mager.story.content.photo.PhotoFragmentBuilder;
import com.mager.story.content.story.StoryFragmentBuilder;
import com.mager.story.menu.audio.MenuAudio;
import com.mager.story.menu.photo.MenuPhoto;
import com.mager.story.menu.story.MenuStory;
import com.mager.story.util.FragmentUtil;
import com.mager.story.util.ResourceUtil;

/**
 * Created by Gerry on 28/10/2016.
 */

class MenuHandler {

    private static final String TAG_PHOTO = "PHOTO";
    private static final String TAG_STORY = "STORY";
    private static final String TAG_AUDIO = "AUDIO";

    private HomeActivity activity;

    MenuHandler(HomeActivity activity) {
        this.activity = activity;
    }

    void goToPhoto(MenuPhoto item) {
        setTitle(R.string.photo_title);
        FragmentUtil.replaceWithBackStack(
                activity,
                PhotoFragmentBuilder.newPhotoFragment(item.getPhotoGroup()),
                TAG_PHOTO
        );
    }

    void goToStory(MenuStory item) {
        setTitle(R.string.story_title);
        FragmentUtil.replaceWithBackStack(activity,
                StoryFragmentBuilder.newStoryFragment(item.getChapter(), item.getTitle()),
                TAG_STORY
        );
    }

    void goToAudio(MenuAudio item) {
        setTitle(R.string.audio_title);
        FragmentUtil.replaceWithBackStack(activity,
                AudioFragmentBuilder.newAudioFragment(item.getName()),
                TAG_AUDIO
        );
    }

    private void setTitle(@StringRes int stringRes) {
        ActionBar actionBar = activity.getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(ResourceUtil.getString(stringRes));
        }
    }
}
