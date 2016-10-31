package com.mager.story.util;

import android.support.annotation.Nullable;

import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * Created by Gerry on 08/10/2016.
 */

public class EncryptionUtil {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

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
    public static SecretKey getSecretKey() {
        try {
            KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
            generator.init(128);
            return generator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Nullable
    public static String decryptText(byte[] encryptedBytes, SecretKey key) {
        byte[] decryptedBytes = getDecryptedBytes(encryptedBytes, key);

        if (decryptedBytes != null) {
            return new String(decryptedBytes, Charset.defaultCharset());
        } else {
            return null;
        }
    }

    @Nullable
    private static byte[] getDecryptedBytes(byte[] encryptedBytes, SecretKey key) {
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
