package com.mager.story.util;

import android.support.annotation.Nullable;

/**
 * Created by Gerry on 08/10/2016.
 */

public class EncryptionUtil {

    @Nullable
    public static String MD5(String key) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(key.getBytes());
            StringBuilder buffer = new StringBuilder();
            for (byte anArray : array) {
                buffer.append(Integer.toHexString((anArray & 0xFF) | 0x100).substring(1, 3));
            }
            return buffer.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}
