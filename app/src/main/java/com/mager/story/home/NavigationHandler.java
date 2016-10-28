package com.mager.story.home;

import android.app.Fragment;
import android.support.design.widget.BottomNavigationView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.mager.story.R;
import com.mager.story.menu.audio.MenuAudioFragment;
import com.mager.story.menu.audio.MenuAudioFragmentBuilder;
import com.mager.story.menu.photo.MenuPhotoFragment;
import com.mager.story.menu.photo.MenuPhotoFragmentBuilder;
import com.mager.story.menu.story.MenuStoryFragment;
import com.mager.story.menu.story.MenuStoryFragmentBuilder;
import com.mager.story.util.FragmentUtil;

import java.util.HashMap;

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

    private String selectedItem;
    private HashMap<String, Integer> tabMapping;

    NavigationHandler(HomeActivity homeActivity, BottomNavigationView bottomView) {
        this.activity = homeActivity;
        this.navigationView = bottomView;
        this.selectedItem = TAG_MENU_PHOTO;
        this.tabMapping = getTabMapping();

        initFragments();
        initListener();
    }

    private HashMap<String, Integer> getTabMapping() {
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put(TAG_MENU_PHOTO, R.id.tab_photo);
        hashMap.put(TAG_MENU_STORY, R.id.tab_story);
        hashMap.put(TAG_MENU_AUDIO, R.id.tab_audio);

        return hashMap;
    }

    boolean initFragments() {
        photoFragment = MenuPhotoFragmentBuilder.newMenuPhotoFragment(activity.getViewModel().getPhotoList());
        storyFragment = MenuStoryFragmentBuilder.newMenuStoryFragment(activity.getViewModel().getStoryList());
        audioFragment = MenuAudioFragmentBuilder.newMenuAudioFragment(activity.getViewModel().getAudioList());

        return true;
    }

    private void initListener() {
        navigationView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.tab_photo:
                            selectItem(TAG_MENU_PHOTO);
                            break;
                        case R.id.tab_story:
                            selectItem(TAG_MENU_STORY);
                            break;
                        case R.id.tab_audio:
                            selectItem(TAG_MENU_AUDIO);
                            break;
                    }

                    return false;
                }
        );
    }

    private void selectItem(String tag) {
        Fragment fragment;

        switch (tag) {
            case TAG_MENU_STORY:
                fragment = storyFragment;
                break;
            case TAG_MENU_AUDIO:
                fragment = audioFragment;
                break;
            default:
                fragment = photoFragment;
                break;
        }

        selectedItem = tag;
        FragmentUtil.replace(activity, fragment, tag);
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
        if (!FragmentUtil.isFragmentVisible(activity, selectedItem)) {
            selectItem(selectedItem);
        }
    }

    void clickItem(String tag) {
        navigationView.findViewById(tabMapping.get(tag)).performClick();
    }

    String getSelectedItem() {
        return selectedItem;
    }
}
