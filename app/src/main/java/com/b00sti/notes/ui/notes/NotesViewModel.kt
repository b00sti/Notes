package com.b00sti.notes.ui.notes

import android.arch.lifecycle.MutableLiveData
import com.b00sti.notes.R
import com.b00sti.notes.api.RxFirebaseDatabase
import com.b00sti.notes.base.BaseViewModel
import com.b00sti.notes.model.Note
import com.b00sti.notes.utils.RxUtils
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy

/**
 * Created by b00sti on 05.06.2018
 */
class NotesViewModel : BaseViewModel<NotesNavigator>() {

    val notesList = MutableLiveData<ArrayList<Note>>()

    fun deleteNote(note: Note) {
        getDisposables().add(RxFirebaseDatabase.deleteNote(note)
                .compose(RxUtils.applyCompletableSchedulers())
                .subscribeBy(
                        onComplete = { refresh() },
                        onError = { getNavigator().showToast(R.string.default_error) }))
    }

    fun refresh() {
        getDisposables().add(getNoteList()
                .compose(RxUtils.applySchedulers())
                .subscribeBy(
                        onSuccess = { updateViews(it) },
                        onError = { getNavigator().showToast(R.string.default_error) }))
    }

    fun searchFor(queredText: String) {
        getDisposables().add(RxFirebaseDatabase.searchNotes(queredText)
                .compose(RxUtils.applySchedulers())
                .subscribeBy(
                        onSuccess = { updateViews(it) },
                        onError = { getNavigator().showToast(R.string.default_error) }))
    }

    private fun getNoteList(): Single<ArrayList<Note>> = RxFirebaseDatabase.getNotes()

    private fun updateViews(notes: ArrayList<Note>) {
        notesList.postValue(notes)
        getNavigator().setNoContentLabelAsVisible(notes.size == 0)
    }

}