package com.b00sti.notes.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by b00sti on 05.06.2018
 */
@Parcelize
data class Note(var desc: String = "", var timestamp: Long = System.currentTimeMillis(), var tag: String = "") : Parcelable
