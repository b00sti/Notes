package com.b00sti.notes.ui.main

import com.b00sti.notes.base.BaseViewModel

/**
 * Created by b00sti on 05.06.2018
 */
class MainViewModel : BaseViewModel<MainNavigator>() {

    fun onNewNoteClicked() = getNavigator().onNewClick()
}