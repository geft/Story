package com.mager.story.home;

import android.support.design.widget.BottomNavigationView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.mager.story.R;

import static com.mager.story.home.HomeActivity.TAG_AUDIO;
import static com.mager.story.home.HomeActivity.TAG_PHOTO;
import static com.mager.story.home.HomeActivity.TAG_STORY;

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
                            activity.insertMenuFragment(activity.photoFragment, TAG_PHOTO);
                            break;
                        case R.id.tab_story:
                            activity.insertMenuFragment(activity.storyFragment, TAG_STORY);
                            break;
                        case R.id.tab_audio:
                            activity.insertMenuFragment(activity.audioFragment, TAG_AUDIO);
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
}
