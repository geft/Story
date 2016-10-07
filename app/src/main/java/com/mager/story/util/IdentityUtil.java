package com.mager.story.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;

import com.mager.story.R;

import java.util.Arrays;

/**
 * Created by Gerry on 08/10/2016.
 */

public class IdentityUtil {

    private Context context;

    public IdentityUtil(Context context) {
        this.context = context;
    }

    public boolean validateIdentity() {
        DeviceIdentity identity = getDeviceIdentity();
        return Arrays.asList(getValidDeviceIds()).contains(identity.deviceId);
    }

    @SuppressLint("HardwareIds")
    private DeviceIdentity getDeviceIdentity() {
        DeviceIdentity identity = new DeviceIdentity();

        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        identity.deviceId = manager.getDeviceId();

        return identity;
    }

    private String[] getValidDeviceIds() {
        return context.getResources().getStringArray(R.array.valid_imei);
    }

    private static class DeviceIdentity {
        String deviceId;
    }
}
