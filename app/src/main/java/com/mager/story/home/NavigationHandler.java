package com.mager.story.home;

import android.app.Fragment;
import android.support.design.widget.BottomNavigationView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.mager.story.R;
import com.mager.story.content.audio.AudioFragmentBuilder;
import com.mager.story.datamodel.MenuDataModel;
import com.mager.story.menu.MenuProvider;
import com.mager.story.menu.audio.MenuAudio;
import com.mager.story.menu.audio.MenuAudioFragment;
import com.mager.story.menu.audio.MenuAudioFragmentBuilder;
import com.mager.story.menu.photo.MenuPhotoFragment;
import com.mager.story.menu.photo.MenuPhotoFragmentBuilder;
import com.mager.story.menu.story.MenuStoryFragment;
import com.mager.story.menu.story.MenuStoryFragmentBuilder;
import com.mager.story.menu.video.MenuVideoFragment;
import com.mager.story.menu.video.MenuVideoFragmentBuilder;
import com.mager.story.util.FragmentUtil;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Gerry on 26/10/2016.
 */

class NavigationHandler {
    private static final String TAG_MENU_PHOTO = "MENU_PHOTO";
    private static final String TAG_MENU_STORY = "MENU_STORY";
    private static final String TAG_MENU_AUDIO = "MENU_AUDIO";
    private static final String TAG_MENU_VIDEO = "MENU_VIDEO";
    private static final String TAG_AUDIO = "AUDIO";

    private BottomNavigationView navigationView;
    private HomeActivity activity;
    private MenuPhotoFragment photoFragment;
    private MenuStoryFragment storyFragment;
    private MenuAudioFragment audioFragment;
    private MenuVideoFragment videoFragment;

    private String selectedItem;
    private HashMap<String, Integer> tabMapping;

    NavigationHandler(HomeActivity homeActivity, BottomNavigationView bottomView) {
        this.activity = homeActivity;
        this.navigationView = bottomView;
        this.selectedItem = TAG_MENU_PHOTO;
        this.tabMapping = getTabMapping();

        init();
    }

    private void init() {
        initListener();
        initFragments();
        initPrimaryFragment();
        animateSlideUp();
    }

    private HashMap<String, Integer> getTabMapping() {
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put(TAG_MENU_PHOTO, R.id.tab_photo);
        hashMap.put(TAG_MENU_STORY, R.id.tab_story);
        hashMap.put(TAG_MENU_AUDIO, R.id.tab_audio);
        hashMap.put(TAG_MENU_VIDEO, R.id.tab_video);

        return hashMap;
    }

    private void initFragments() {
        MenuProvider provider = new MenuProvider();
        MenuDataModel menuDataModel = activity.getViewModel().getMenuDataModel();

        photoFragment = MenuPhotoFragmentBuilder.newMenuPhotoFragment(
                provider.convertDataModelToMenuPhoto(menuDataModel)
        );
        storyFragment = MenuStoryFragmentBuilder.newMenuStoryFragment(
                provider.convertDataModelToMenuStory(menuDataModel)
        );
        audioFragment = MenuAudioFragmentBuilder.newMenuAudioFragment(new ArrayList<>());
        videoFragment = MenuVideoFragmentBuilder.newMenuVideoFragment(
                provider.convertDataModelToMenuVideo(menuDataModel)
        );
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
                        case R.id.tab_video:
                            selectItem(TAG_MENU_VIDEO);
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
            case TAG_MENU_VIDEO:
                fragment = videoFragment;
                break;
            default:
                fragment = photoFragment;
                break;
        }

        selectedItem = tag;

        if (!fragment.isAdded()) {
            FragmentUtil.replace(activity, fragment, tag);
        }
    }

    private void animateSlideUp() {
        if (navigationView.isShown()) return;

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

    private void initPrimaryFragment() {
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

    void goToAudio(MenuAudio item) {
        FragmentUtil.replaceWithBackStack(
                activity,
                AudioFragmentBuilder.newAudioFragment(item.getCode(), item.getName()),
                TAG_AUDIO
        );
    }
}
