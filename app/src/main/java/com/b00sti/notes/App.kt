package com.b00sti.notes

import android.app.Application
import android.content.Context
import com.b00sti.notes.api.RxFirebaseDatabase

/**
 * Created by b00sti on 05.06.2018
 */
class App : Application() {

    init {
        instance = this
    }

    companion object {
        private lateinit var instance: App

        fun appCtx(): Context {
            return instance.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        RxFirebaseDatabase.setUpDatabase()
    }
}