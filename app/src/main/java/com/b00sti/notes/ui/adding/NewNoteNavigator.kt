package com.b00sti.notes.ui.adding

import com.b00sti.notes.base.BaseNavigator

/**
 * Created by b00sti on 05.06.2018
 */
interface NewNoteNavigator : BaseNavigator {

    fun onNoteUpdated()
    fun prepareNoteFromViews()

}