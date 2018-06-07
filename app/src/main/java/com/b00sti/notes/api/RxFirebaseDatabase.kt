package com.b00sti.notes.api

import com.b00sti.notes.model.Note
import com.google.firebase.database.*
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*

/**
 * Created by b00sti on 07.06.2018
 */
object RxFirebaseDatabase : IFirebaseDataBase {

    private lateinit var database: FirebaseDatabase
    private lateinit var databaseMain: DatabaseReference

    private const val MAIN = "main"

    fun setUpDatabase() {
        database = FirebaseDatabase.getInstance()
        database.setPersistenceEnabled(true)
        database.setLogLevel(Logger.Level.DEBUG)
        databaseMain = database.reference.child(MAIN)
        databaseMain.keepSynced(true)
    }

    override fun updateNote(note: Note?): Completable = Completable.create({ emitter ->
        if (note == null) emitter.onError(NullPointerException())
        val map = WeakHashMap<String, Any>()
        map[note?.timestamp.toString()] = note
        databaseMain.updateChildren(map).addOnCompleteListener({ task ->
            if (task.isSuccessful) emitter.onComplete()
            else task.exception?.let { emitter.onError(it) }
        })
    })

    override fun deleteNote(note: Note?): Completable = Completable.create({ emitter ->
        if (note == null) emitter.onError(NullPointerException())
        databaseMain.child(note?.timestamp.toString()).removeValue().addOnCompleteListener({ task ->
            if (task.isSuccessful) emitter.onComplete()
            else task.exception?.let { emitter.onError(it) }
        })
    })

    override fun getNotes(): Single<ArrayList<Note>> = Single.create { emitter ->
        databaseMain.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError?) {
                if (error != null) emitter.onError(error.toException())
                else emitter.onError(NullPointerException("Canceled with null value!"))
            }

            override fun onDataChange(data: DataSnapshot?) {
                when {
                    data == null || data.childrenCount == 0L -> emitter.onError(NullPointerException())
                    else -> {
                        val notes = ArrayList<Note>()
                        for (entry in data.children) {
                            try {
                                entry.getValue(Note::class.java)?.let { notes.add(it) }
                            } catch (e: DatabaseException) {
                                e.printStackTrace()
                            }
                        }
                        notes.sortByDescending(Note::timestamp)
                        emitter.onSuccess(notes)
                    }
                }
            }
        })
    }

    override fun searchNotes(searchText: String): Single<ArrayList<Note>> = Single.create { emitter ->
        databaseMain.orderByChild("tag").startAt("#" + searchText).endAt("#" + searchText + "\uf8ff")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError?) {
                        if (error != null) emitter.onError(error.toException())
                        else emitter.onError(NullPointerException("Canceled with null value!"))
                    }

                    override fun onDataChange(data: DataSnapshot?) {
                        val notes = ArrayList<Note>()
                        when {
                            data == null || data.childrenCount == 0L -> emitter.onSuccess(notes)
                            else -> {
                                for (entry in data.children) {
                                    try {
                                        entry.getValue(Note::class.java)?.let { notes.add(it) }
                                    } catch (e: DatabaseException) {
                                        e.printStackTrace()
                                    }
                                }
                                notes.sortByDescending(Note::timestamp)
                                emitter.onSuccess(notes)
                            }
                        }
                    }
                })
    }

}