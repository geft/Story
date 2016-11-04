package com.mager.story.util;

/**
 * Created by Gerry on 04/11/2016.
 */

public class StringUtil {
    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static String padLeft(String padding, String content, int maxLength) {
        StringBuilder builder = new StringBuilder();

        for (int toPrepend = maxLength - content.length(); toPrepend > 0; toPrepend--) {
            builder.append(padding);
        }

        builder.append(content);
        return builder.toString();
    }
}
