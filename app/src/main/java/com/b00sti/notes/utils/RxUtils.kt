package com.b00sti.notes.utils

import io.reactivex.CompletableTransformer
import io.reactivex.Scheduler
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by b00sti on 07.06.2018
 */
object RxUtils {

    fun <T> applySchedulers(): SingleTransformer<T, T> {
        return SingleTransformer { upstream ->
            upstream.subscribeOn(io())
                    .observeOn(ui())
        }
    }

    fun applyCompletableSchedulers(): CompletableTransformer {
        return CompletableTransformer { upstream ->
            upstream.subscribeOn(io())
                    .observeOn(ui())
        }
    }

    fun ui(): Scheduler = AndroidSchedulers.mainThread()
    fun io(): Scheduler = Schedulers.io()
    fun computation(): Scheduler = Schedulers.computation()

}