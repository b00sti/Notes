package com.b00sti.notes.utils

import android.os.Bundle
import android.support.v4.app.Fragment

/**
 * Created by b00sti on 05.06.2018
 */

inline fun <FRAGMENT : Fragment> FRAGMENT.putArgs(argsBuilder: Bundle.() -> Unit): FRAGMENT = this.apply { arguments = Bundle().apply(argsBuilder) }