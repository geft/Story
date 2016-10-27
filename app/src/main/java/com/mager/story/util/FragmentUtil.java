package com.mager.story.util;

import android.app.Activity;
import android.app.Fragment;
import android.support.annotation.IdRes;

import com.mager.story.R;

/**
 * Created by Gerry on 28/10/2016.
 */

public class FragmentUtil {

    public static boolean isFragmentVisible(Activity activity, String tag) {
        Fragment fragment = activity.getFragmentManager().findFragmentByTag(tag);
        return fragment != null && fragment.isVisible();
    }

    public static void insert(Activity activity, Fragment fragment, String tag) {
        activity.getFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .add(R.id.container, fragment, tag)
                .commit();
    }

    public static void insert(Activity activity, Fragment fragment, String tag, @IdRes int container) {
        activity.getFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .add(container, fragment, tag)
                .commit();
    }

    public static void replace(Activity activity, Fragment fragment, String tag) {
        activity.getFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(R.id.container, fragment, tag)
                .commit();
    }

    public static void replace(Activity activity, Fragment fragment, String tag, @IdRes int container) {
        activity.getFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(container, fragment, tag)
                .commit();
    }

    public static void replaceWithBackStack(Activity activity, Fragment fragment, String tag) {
        activity.getFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(R.id.container, fragment, tag)
                .addToBackStack(null)
                .commit();
    }

    public static void replaceWithBackStack(Activity activity, Fragment fragment, String tag, @IdRes int container) {
        activity.getFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(container, fragment, tag)
                .addToBackStack(null)
                .commit();
    }
}
