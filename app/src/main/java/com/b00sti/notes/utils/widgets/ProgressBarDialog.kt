package com.b00sti.notes.utils.widgets

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.b00sti.notes.R

/**
 * Created by b00sti on 05.06.2018
 */
class ProgressBarDialog : DialogFragment() {

    companion object {
        fun getInstance() = ProgressBarDialog()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        return activity?.layoutInflater?.inflate(R.layout.dialog_progress, container, false)
    }

}