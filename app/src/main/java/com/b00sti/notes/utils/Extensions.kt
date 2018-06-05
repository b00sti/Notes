package com.b00sti.notes.utils

import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import com.b00sti.notes.R
import com.b00sti.notes.utils.widgets.ProgressBarDialog

/**
 * Created by b00sti on 05.06.2018
 */
fun AppCompatActivity.showProgressDialog(): DialogFragment? {
    val dialog = ProgressBarDialog.getInstance()
    dialog.setStyle(DialogFragment.STYLE_NO_FRAME, R.style.AppDialog)
    dialog.show(this.supportFragmentManager, dialog.tag)
    return dialog
}

fun AppCompatActivity.hideProgressDialog(dialog: DialogFragment?) {
    this.runOnUiThread({ dialog?.dismissAllowingStateLoss() })
}