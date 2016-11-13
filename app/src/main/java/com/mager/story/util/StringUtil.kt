package com.mager.story.util

/**
 * Created by Gerry on 04/11/2016.
 */

object StringUtil {

    fun padLeft(padding: String, content: String, maxLength: Int): String {
        val builder = StringBuilder()

        for (index in maxLength - content.length downTo 1) {
            builder.append(padding)
        }

        builder.append(content)
        return builder.toString()
    }
}
