package com.b00sti.notes.api

import com.b00sti.notes.model.Note
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by b00sti on 07.06.2018
 */
interface IFirebaseDataBase {

    fun updateNote(note: Note?): Completable
    fun getNotes(): Single<ArrayList<Note>>
    fun deleteNote(note: Note?): Completable
    fun searchNotes(searchText: String): Single<java.util.ArrayList<Note>>

}