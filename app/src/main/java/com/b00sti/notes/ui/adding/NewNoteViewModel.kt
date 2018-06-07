package com.b00sti.notes.ui.adding

import android.databinding.ObservableField
import com.b00sti.notes.R
import com.b00sti.notes.api.RxFirebaseDatabase
import com.b00sti.notes.base.BaseViewModel
import com.b00sti.notes.model.Note
import com.b00sti.notes.utils.RxUtils
import io.reactivex.rxkotlin.subscribeBy

/**
 * Created by b00sti on 05.06.2018
 */
class NewNoteViewModel : BaseViewModel<NewNoteNavigator>() {
    val note = ObservableField<Note>()

    fun updateNote() {
        prepareNote()
        when {
            isNoteValidatedWithSuccess() -> addNote()
            else -> getNavigator().showToast(R.string.validation_prompt)
        }
    }

    private fun addNote() {
        getDisposables().add(
                RxFirebaseDatabase.updateNote(note.get())
                        .compose(RxUtils.applyCompletableSchedulers())
                        .doOnSubscribe({ getNavigator().onStartLoading() })
                        .doAfterTerminate({ getNavigator().onFinishLoading() })
                        .subscribeBy(
                                onComplete = { getNavigator().onNoteUpdated() },
                                onError = { getNavigator().showToast(R.string.default_error) })
        )
    }

    private fun isNoteValidatedWithSuccess(): Boolean =
            !note.get()?.tag.isNullOrBlank().or(note.get()?.desc.isNullOrBlank())

    private fun prepareNote() = getNavigator().prepareNoteFromViews()

}