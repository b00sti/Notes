package com.b00sti.notes.base

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by b00sti on 05.06.2018
 */
abstract class BaseViewModel<N : BaseNavigator> : ViewModel() {
    private lateinit var mNavigator: N
    private var mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    fun getNavigator(): N = mNavigator

    fun setNavigator(navigator: N) {
        this.mNavigator = navigator
    }

    override fun onCleared() {
        super.onCleared()
        mCompositeDisposable.clear()
    }

    fun getDisposables(): CompositeDisposable {
        initDisposables()
        return mCompositeDisposable
    }

    private fun initDisposables() {
        if (mCompositeDisposable.isDisposed) {
            mCompositeDisposable = CompositeDisposable()
        }
    }

}