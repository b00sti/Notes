package com.b00sti.notes.base

import android.support.annotation.StringRes
import android.widget.Toast

/**
 * Created by b00sti on 05.06.2018
 */
interface BaseNavigator {

    fun showToast(@StringRes resMsg: Int): Toast?
    fun onStartLoading(): Unit?
    fun onFinishLoading(): Unit?

}