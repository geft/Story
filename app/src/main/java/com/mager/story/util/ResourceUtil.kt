package com.mager.story.util

import android.graphics.drawable.Drawable
import android.support.annotation.*
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.Toast
import com.mager.story.R
import com.mager.story.StoryApplication
import com.mager.story.constant.EnumConstant.SnackBarType

/**
 * Created by Gerry on 25/09/2016.
 */

object ResourceUtil {

    private val instance: StoryApplication
        get() = StoryApplication.getInstance()

    fun showToast(text: String) {
        Toast.makeText(instance, text, Toast.LENGTH_SHORT).show()
    }

    fun showSnackBar(container: View, message: String, @SnackBarType type: String) {
        val snackBar = Snackbar.make(container, message, Snackbar.LENGTH_SHORT)
        val backgroundColor: Int

        when (type) {
            SnackBarType.NORMAL -> backgroundColor = getColor(R.color.black)
            SnackBarType.ERROR -> backgroundColor = getColor(R.color.red)
            else -> backgroundColor = getColor(R.color.primary)
        }

        snackBar.view.setBackgroundColor(backgroundColor)
        snackBar.setActionTextColor(getColor(R.color.white))
        snackBar.show()
    }

    fun showErrorSnackBar(container: View, message: String) {
        showSnackBar(container, message, SnackBarType.ERROR)
    }

    fun getColor(@ColorRes colorRes: Int): Int {
        return ContextCompat.getColor(instance, colorRes)
    }

    fun getDrawable(@DrawableRes drawableRes: Int): Drawable {
        return ContextCompat.getDrawable(instance, drawableRes)
    }

    fun getDimenInPx(@DimenRes dimenRes: Int): Int {
        return instance.resources.getDimension(dimenRes).toInt()
    }

    fun getDimenInDp(@DimenRes dimenRes: Int): Int {
        return ViewUtil.pxToDp(instance, getDimenInPx(dimenRes))
    }

    fun getString(@StringRes stringRes: Int): String {
        return instance.getString(stringRes)
    }

    fun getString(@StringRes stringRes: Int, vararg args: String): String {
        return instance.getString(stringRes, *args as Array<*>)
    }

    fun getQuantityString(@PluralsRes pluralsRes: Int, quantity: Int): String {
        return instance.resources.getQuantityString(pluralsRes, quantity)
    }

    fun getStringArray(@ArrayRes arrayRes: Int): Array<String> {
        return instance.resources.getStringArray(arrayRes)
    }
}
