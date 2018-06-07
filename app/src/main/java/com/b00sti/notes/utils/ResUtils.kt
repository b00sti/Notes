package com.b00sti.notes.utils

import android.support.annotation.StringRes
import com.b00sti.notes.App

/**
 * Created by b00sti on 05.06.2018
 */
object ResUtils {
    fun getString(@StringRes id: Int): String = App.appCtx().getString(id)
}