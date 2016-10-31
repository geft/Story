package com.mager.story.util;

import android.support.annotation.Nullable;

import java.nio.charset.Charset;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Gerry on 08/10/2016.
 */

public class EncryptionUtil {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String KEY_ALGORITHM = "AES";

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

    @Nullable
    public SecretKey getSecretKey(byte[] bytes) {
        return new SecretKeySpec(bytes, 0, 128, KEY_ALGORITHM);
    }

    @Nullable
    public String decryptText(byte[] encryptedBytes, SecretKey key) {
        byte[] decryptedBytes = getDecryptedBytes(encryptedBytes, key);

        if (decryptedBytes != null) {
            return new String(decryptedBytes, Charset.defaultCharset());
        } else {
            return null;
        }
    }

    @Nullable
    private byte[] getDecryptedBytes(byte[] encryptedBytes, SecretKey key) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);

            return cipher.doFinal(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
