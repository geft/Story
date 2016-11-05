package com.mager.story.util;

import android.graphics.drawable.Drawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.PluralsRes;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.mager.story.R;
import com.mager.story.StoryApplication;
import com.mager.story.constant.EnumConstant.SnackBarType;

/**
 * Created by Gerry on 25/09/2016.
 */

public class ResourceUtil {

    public static void showToast(String text) {
        Toast.makeText(getInstance(), text, Toast.LENGTH_SHORT).show();
    }

    public static void showSnackBar(View container, String message, @SnackBarType String type) {
        Snackbar snackbar = Snackbar.make(container, message, Snackbar.LENGTH_SHORT);
        int backgroundColor;

        switch (type) {
            case SnackBarType.NORMAL:
                backgroundColor = getColor(R.color.black);
                break;
            case SnackBarType.ERROR:
                backgroundColor = getColor(R.color.red);
                break;
            default:
                backgroundColor = getColor(R.color.primary);
        }

        snackbar.getView().setBackgroundColor(backgroundColor);
        snackbar.setActionTextColor(getColor(R.color.white));
        snackbar.show();
    }

    public static void showErrorSnackBar(View container, String message) {
        showSnackBar(container, message, SnackBarType.ERROR);
    }

    public static int getColor(@ColorRes int colorRes) {
        return ContextCompat.getColor(getInstance(), colorRes);
    }

    public static Drawable getDrawable(@DrawableRes int drawableRes) {
        return ContextCompat.getDrawable(getInstance(), drawableRes);
    }

    public static int getDimenInPx(@DimenRes int dimenRes) {
        return (int) getInstance().getResources().getDimension(dimenRes);
    }

    public static int getDimenInDp(@DimenRes int dimenRes) {
        return ViewUtil.pxToDp(getInstance(), getDimenInPx(dimenRes));
    }

    public static String getString(@StringRes int stringRes) {
        return getInstance().getString(stringRes);
    }

    public static String getString(@StringRes int stringRes, String... args) {
        return getInstance().getString(stringRes, (Object[]) args);
    }

    public static String getQuantityString(@PluralsRes int pluralsRes, int quantity) {
        return getInstance().getResources().getQuantityString(pluralsRes, quantity);
    }

    public static String[] getStringArray(@ArrayRes int arrayRes) {
        return getInstance().getResources().getStringArray(arrayRes);
    }

    private static StoryApplication getInstance() {
        return StoryApplication.getInstance();
    }
}
