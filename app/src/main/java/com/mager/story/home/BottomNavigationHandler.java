package com.mager.story.home;

import android.support.design.widget.BottomNavigationView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.mager.story.R;

import static com.mager.story.home.HomeActivity.TAG_MENU_AUDIO;
import static com.mager.story.home.HomeActivity.TAG_MENU_PHOTO;
import static com.mager.story.home.HomeActivity.TAG_MENU_STORY;

/**
 * Created by Gerry on 26/10/2016.
 */

class BottomNavigationHandler {
    private BottomNavigationView navigationView;
    private HomeActivity activity;

    BottomNavigationHandler(HomeActivity activity, BottomNavigationView navigationView) {
        this.activity = activity;
        this.navigationView = navigationView;

        initListener();
    }

    private void initListener() {
        navigationView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.tab_photo:
                            activity.insertFragment(activity.photoFragment, TAG_MENU_PHOTO);
                            break;
                        case R.id.tab_story:
                            activity.insertFragment(activity.storyFragment, TAG_MENU_STORY);
                            break;
                        case R.id.tab_audio:
                            activity.insertFragment(activity.audioFragment, TAG_MENU_AUDIO);
                            break;
                        default:
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
}
