package com.mager.story.home;

import android.app.Fragment;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.mager.story.R;
import com.mager.story.content.audio.AudioFragmentBuilder;
import com.mager.story.content.photo.PhotoFragmentBuilder;
import com.mager.story.content.story.StoryFragmentBuilder;
import com.mager.story.content.video.VideoFragmentBuilder;
import com.mager.story.datamodel.MenuDataModel;
import com.mager.story.menu.MenuProvider;
import com.mager.story.menu.audio.MenuAudio;
import com.mager.story.menu.audio.MenuAudioFragment;
import com.mager.story.menu.audio.MenuAudioFragmentBuilder;
import com.mager.story.menu.photo.MenuPhoto;
import com.mager.story.menu.photo.MenuPhotoFragment;
import com.mager.story.menu.photo.MenuPhotoFragmentBuilder;
import com.mager.story.menu.story.MenuStory;
import com.mager.story.menu.story.MenuStoryFragment;
import com.mager.story.menu.story.MenuStoryFragmentBuilder;
import com.mager.story.menu.video.MenuVideo;
import com.mager.story.menu.video.MenuVideoFragment;
import com.mager.story.menu.video.MenuVideoFragmentBuilder;
import com.mager.story.util.FragmentUtil;
import com.mager.story.util.ResourceUtil;

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
    private static final String TAG_PHOTO = "PHOTO";
    private static final String TAG_STORY = "STORY";
    private static final String TAG_AUDIO = "AUDIO";
    private static final String TAG_VIDEO = "VIDEO";

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
    }

    void init() {
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

    private boolean initFragments() {
        MenuProvider provider = new MenuProvider();
        MenuDataModel menuDataModel = activity.getViewModel().getMenuDataModel();

        photoFragment = MenuPhotoFragmentBuilder.newMenuPhotoFragment(
                provider.convertDataModelToMenuPhoto(menuDataModel)
        );
        storyFragment = MenuStoryFragmentBuilder.newMenuStoryFragment(
                provider.convertDataModelToMenuStory(menuDataModel)
        );
        audioFragment = MenuAudioFragmentBuilder.newMenuAudioFragment(new ArrayList<>());
        videoFragment = MenuVideoFragmentBuilder.newMenuVideoFragment(new ArrayList<>());

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

    boolean isMenuVisible() {
        return FragmentUtil.isFragmentVisible(activity, TAG_MENU_PHOTO) ||
                FragmentUtil.isFragmentVisible(activity, TAG_MENU_STORY) ||
                FragmentUtil.isFragmentVisible(activity, TAG_MENU_AUDIO) ||
                FragmentUtil.isFragmentVisible(activity, TAG_MENU_VIDEO);
    }

    void goToPhoto(MenuPhoto item) {
        setTitle(item.getName());
        FragmentUtil.replaceWithBackStack(
                activity,
                PhotoFragmentBuilder.newPhotoFragment(item.getCode(), item.getCount()),
                TAG_PHOTO
        );
    }

    void goToStory(MenuStory item) {
        setTitle(ResourceUtil.getString(R.string.story_header_format, item.getChapter(), item.getTitle()));
        FragmentUtil.replaceWithBackStack(
                activity,
                StoryFragmentBuilder.newStoryFragment(item.getChapter(), item.getCode(), item.getTitle()),
                TAG_STORY
        );
    }

    void goToAudio(MenuAudio item) {
        setTitle(item.getName());
        FragmentUtil.replaceWithBackStack(
                activity,
                AudioFragmentBuilder.newAudioFragment(item.getCode(), item.getName()),
                TAG_AUDIO
        );
    }

    void goToVideo(MenuVideo item) {
        setTitle(item.getName());
        FragmentUtil.replaceWithBackStack(
                activity,
                VideoFragmentBuilder.newVideoFragment(item.getCode(), item.getName()),
                TAG_VIDEO
        );
    }

    private void setTitle(String title) {
        ActionBar actionBar = activity.getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }
}
