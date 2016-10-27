package com.mager.story.home;

import android.support.design.widget.BottomNavigationView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.mager.story.R;
import com.mager.story.menu.audio.MenuAudio;
import com.mager.story.menu.audio.MenuAudioFragment;
import com.mager.story.menu.audio.MenuAudioFragmentBuilder;
import com.mager.story.menu.photo.MenuPhoto;
import com.mager.story.menu.photo.MenuPhotoFragment;
import com.mager.story.menu.photo.MenuPhotoFragmentBuilder;
import com.mager.story.menu.story.MenuStory;
import com.mager.story.menu.story.MenuStoryFragment;
import com.mager.story.menu.story.MenuStoryFragmentBuilder;
import com.mager.story.util.FragmentUtil;

import java.util.List;

/**
 * Created by Gerry on 26/10/2016.
 */

class NavigationHandler {
    private static final String TAG_MENU_PHOTO = "MENU_PHOTO";
    private static final String TAG_MENU_STORY = "MENU_STORY";
    private static final String TAG_MENU_AUDIO = "MENU_AUDIO";

    private BottomNavigationView navigationView;
    private HomeActivity activity;
    private MenuPhotoFragment photoFragment;
    private MenuStoryFragment storyFragment;
    private MenuAudioFragment audioFragment;

    NavigationHandler(HomeActivity activity, BottomNavigationView navigationView) {
        this.activity = activity;
        this.navigationView = navigationView;

        initListener();
    }

    boolean initFragments(List<MenuPhoto> photoList, List<MenuStory> storyList, List<MenuAudio> audioList) {
        photoFragment = MenuPhotoFragmentBuilder.newMenuPhotoFragment(photoList);
        storyFragment = MenuStoryFragmentBuilder.newMenuStoryFragment(storyList);
        audioFragment = MenuAudioFragmentBuilder.newMenuAudioFragment(audioList);

        return true;
    }

    private void initListener() {
        navigationView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.tab_photo:
                            FragmentUtil.replace(activity, photoFragment, TAG_MENU_PHOTO);
                            break;
                        case R.id.tab_story:
                            FragmentUtil.replace(activity, storyFragment, TAG_MENU_STORY);
                            break;
                        case R.id.tab_audio:
                            FragmentUtil.replace(activity, audioFragment, TAG_MENU_AUDIO);
                            break;
                    }

                    return false;
                }
        );
    }

    void animateSlideUp() {
        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.slide_down_to_center);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                navigationView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        navigationView.startAnimation(animation);
    }

    void animateSlideDown() {
        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.slide_center_to_down);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                navigationView.setVisibility(View.GONE);
                navigationView.findViewById(R.id.tab_photo).performClick();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    void initPrimaryFragment() {
        if (!FragmentUtil.isFragmentVisible(activity, TAG_MENU_PHOTO)) {
            FragmentUtil.replace(activity, photoFragment, TAG_MENU_PHOTO);
        }
    }

    boolean isMenuVisible() {
        return FragmentUtil.isFragmentVisible(activity, TAG_MENU_PHOTO) ||
                FragmentUtil.isFragmentVisible(activity, TAG_MENU_STORY) ||
                FragmentUtil.isFragmentVisible(activity, TAG_MENU_AUDIO);
    }
}
