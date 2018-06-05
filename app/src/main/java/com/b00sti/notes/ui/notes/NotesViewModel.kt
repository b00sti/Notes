package com.b00sti.notes.ui.notes

import android.arch.lifecycle.MutableLiveData
import com.b00sti.notes.base.BaseViewModel
import com.b00sti.notes.model.Note
import io.reactivex.Observable

/**
 * Created by b00sti on 05.06.2018
 */
class NotesViewModel : BaseViewModel<NotesNavigator>() {

    val notesList = MutableLiveData<List<Note>>()

    fun refresh() {
        getBuckets()
                .doOnSubscribe({ getNavigator().onStartLoading() })
                .doOnTerminate({ getNavigator().onFinishLoading() })
                .subscribe { notesList.postValue(it) }
    }

    fun getBuckets(): Observable<List<Note>> {
        return Observable.create { emitter ->
            val items = listOf(
                    Note("Europe" + System.currentTimeMillis(), 10, "eur"),
                    Note("Asia" + System.currentTimeMillis(), 10, "asi"),
                    Note("Africa" + System.currentTimeMillis(), 10, "afr")
            )
            val list = notesList.value?.toMutableList()
            list?.addAll(items)
            if (list != null) {
                emitter.onNext(list)
            } else {
                emitter.onNext(items)
            }
            emitter.onComplete()
        }
    }

}