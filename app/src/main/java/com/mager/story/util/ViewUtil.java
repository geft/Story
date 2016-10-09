package com.mager.story.util;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by Gerry on 09/10/2016.
 */

public class ViewUtil {

    public static ScreenSize getScreenSize(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;

        ScreenSize screenSize = new ScreenSize();
        screenSize.dpHeight = dpHeight;
        screenSize.dpWidth = dpWidth;

        return screenSize;
    }

    public static int calculateSpanCount(Context context, int itemDpWidth) {
        ScreenSize screenSize = getScreenSize(context);
        int spanCount = (int) screenSize.dpWidth / itemDpWidth;
        return spanCount == 0 ? 1 : spanCount;
    }

    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static int pxToDp(Context context, int px) {
        return (int) (px / getDensity(context));
    }

    public static int dpToPx(Context context, int dp) {
        return (int) (dp * getDensity(context));
    }

    public static class ScreenSize {
        public float dpHeight;
        public float dpWidth;
    }
}
