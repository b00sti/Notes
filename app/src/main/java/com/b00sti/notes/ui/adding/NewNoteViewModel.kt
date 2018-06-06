package com.b00sti.notes.ui.adding

import android.databinding.ObservableField
import com.b00sti.notes.base.BaseViewModel
import com.b00sti.notes.model.Note

/**
 * Created by b00sti on 05.06.2018
 */
class NewNoteViewModel : BaseViewModel<NewNoteNavigator>() {
    val note = ObservableField<Note>()
}