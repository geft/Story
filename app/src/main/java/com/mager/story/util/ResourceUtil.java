package com.mager.story.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.mager.story.R;

/**
 * Created by Gerry on 25/09/2016.
 */

public class ResourceUtil {

    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void showSnackbar(View view, String text) {
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(getColor(view.getContext(), R.color.primary));
        snackbar.show();
    }

    public static int getColor(Context context, int resId) {
        return ContextCompat.getColor(context, resId);
    }

    public static Drawable getDrawable(Context context, int resId) {
        return ContextCompat.getDrawable(context, resId);
    }
}
