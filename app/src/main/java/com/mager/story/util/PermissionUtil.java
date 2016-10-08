package com.mager.story.util;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.mager.story.constant.EnumConstant.PermissionType;
import com.mager.story.constant.EnumConstant.RequestCode;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * Created by Gerry on 08/10/2016.
 */

public class PermissionUtil {

    private Context context;

    public PermissionUtil(Context context) {
        this.context = context;
    }

    public boolean isPermissionGranted(@PermissionType String permission) {
        if (!checkPermission(permission)) {
            requestPermission(permission);
            return false;
        } else {
            return true;
        }
    }

    private boolean checkPermission(@PermissionType String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PERMISSION_GRANTED;
    }

    private void requestPermission(@PermissionType String permission) {
        ActivityCompat.requestPermissions((Activity) context, new String[]{permission}, RequestCode.PERMISSION);
    }
}
