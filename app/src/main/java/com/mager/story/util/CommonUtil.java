package com.mager.story.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.mager.story.R;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Gerry on 22/10/2016.
 */

public class CommonUtil {

    public static <T> Observable.Transformer<T, T> getCommonTransformer() {
        return observable -> observable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static void showKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(activity.getCurrentFocus(), 0);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View currentFocus = activity.getCurrentFocus();

        if (currentFocus != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }

    @SuppressWarnings("deprecation")
    public static boolean isDisplayOn(Activity activity) {
        PowerManager powerManager = (PowerManager) activity.getSystemService(Context.POWER_SERVICE);

        if (Build.VERSION.SDK_INT <= 19) {
            return powerManager.isScreenOn();
        } else {
            return powerManager.isInteractive();
        }
    }

    public static void goToPlayStore(Context context) {
        final String appPackageName = context.getPackageName();
        final String link = ResourceUtil.getString(R.string.update_link_format, appPackageName);
        final String linkDirect = ResourceUtil.getString(R.string.update_link_direct_format, appPackageName);

        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
        } catch (android.content.ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(linkDirect)));
        }
    }

    public static boolean doesAppNeedUpdate(Context context, long version) throws PackageManager.NameNotFoundException {
        return version > context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
    }
}
