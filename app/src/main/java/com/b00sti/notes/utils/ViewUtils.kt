package com.b00sti.notes.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

/**
 * Created by b00sti on 05.06.2018
 */
object ViewUtils {

    fun hideSoftInput(activity: Activity) {
        val view = activity.currentFocus
        if (view != null) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}