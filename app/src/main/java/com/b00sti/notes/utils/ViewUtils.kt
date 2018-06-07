package com.b00sti.notes.utils

import android.app.Activity
import android.content.Context
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.b00sti.notes.R
import com.b00sti.notes.utils.widgets.ProgressBarDialog

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

fun AppCompatActivity.showProgressDialog(): DialogFragment? {
    val dialog = ProgressBarDialog.getInstance()
    dialog.setStyle(DialogFragment.STYLE_NO_FRAME, R.style.AppDialog)
    dialog.show(this.supportFragmentManager, dialog.tag)
    return dialog
}

fun AppCompatActivity.hideProgressDialog(dialog: DialogFragment?) {
    this.runOnUiThread({ dialog?.dismissAllowingStateLoss() })
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}