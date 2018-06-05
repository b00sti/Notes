package com.b00sti.notes.utils

import android.graphics.drawable.Drawable
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import com.b00sti.notes.App
import com.b00sti.notes.R

/**
 * Created by b00sti on 05.06.2018
 */
object ResUtils {
    fun getAccentColor(): Int = ResUtils.getColor(R.color.colorAccent)
    fun getPrimaryColor(): Int = ResUtils.getColor(R.color.colorPrimary)
    fun getPrimaryDarkColor(): Int = ResUtils.getColor(R.color.colorPrimaryDark)
    fun getColor(@ColorRes id: Int): Int = ContextCompat.getColor(App.appCtx(), id)
    fun getString(@StringRes id: Int): String = App.appCtx().getString(id)
    fun getDrawable(@DrawableRes id: Int): Drawable? = ContextCompat.getDrawable(App.appCtx(), id)
}