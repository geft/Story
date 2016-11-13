package com.mager.story.util;

import android.support.annotation.Nullable;

import java.security.MessageDigest;

/**
 * Created by Gerry on 08/10/2016.
 */

public class EncryptionUtil {

    @Nullable
    public static String encrypt(String text) {
        return getSHA512(text);
    }

    @Nullable
    private static String getSHA512(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.update(text.getBytes("iso-8859-1"), 0, text.length());
            return convertToHex(digest.digest());
        } catch (Exception e) {
            LogUtil.INSTANCE.logError(e);
        }
        return null;
    }

    private static String convertToHex(byte[] data) {
        StringBuilder builder = new StringBuilder();
        for (byte aData : data) {
            int halfByte = (aData >>> 4) & 0x0F;
            int twoHalves = 0;
            do {
                if ((0 <= halfByte) && (halfByte <= 9))
                    builder.append((char) ('0' + halfByte));
                else
                    builder.append((char) ('a' + (halfByte - 10)));
                halfByte = aData & 0x0F;
            } while (twoHalves++ < 1);
        }

        return builder.toString();
    }

    public static byte[] flip(byte[] bytes) {
        byte[] encrypted = new byte[bytes.length];

        for (int i = 0; i < bytes.length; i++) {
            encrypted[i] = (byte) ~bytes[i];
        }
        return encrypted;
    }

    public static byte[] flipFirst100(byte[] bytes) {
        byte[] encrypted = new byte[bytes.length];

        for (int i = 0; i < bytes.length; i++) {

            if (i < 100) {
                encrypted[i] = (byte) ~bytes[i];
            } else {
                encrypted[i] = bytes[i];
            }
        }
        return encrypted;
    }
}
