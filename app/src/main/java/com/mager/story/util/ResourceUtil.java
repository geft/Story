package com.mager.story.util;

import android.app.Activity;
import android.graphics.drawable.Drawable;
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
        Toast.makeText(StoryApplication.getInstance(), text, Toast.LENGTH_SHORT).show();
    }

    public static void showSnackBar(Activity activity, @StringRes int stringRes, @SnackBarType String type) {
        View view = activity.findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(view, stringRes, Snackbar.LENGTH_SHORT);
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

    public static int getColor(@ColorRes int colorRes) {
        return ContextCompat.getColor(StoryApplication.getInstance(), colorRes);
    }

    public static Drawable getDrawable(@DrawableRes int drawableRes) {
        return ContextCompat.getDrawable(StoryApplication.getInstance(), drawableRes);
    }

    public static int getDimenInPx(@DimenRes int dimenRes) {
        return (int) StoryApplication.getInstance().getResources().getDimension(dimenRes);
    }

    public static int getDimenInDp(@DimenRes int dimenRes) {
        return ViewUtil.pxToDp(StoryApplication.getInstance(), getDimenInPx(dimenRes));
    }

    public static String getString(@StringRes int stringRes) {
        return StoryApplication.getInstance().getString(stringRes);
    }

    public static String getQuantityString(@PluralsRes int pluralsRes, int quantity) {
        return StoryApplication.getInstance().getResources().getQuantityString(pluralsRes, quantity);
    }
}
