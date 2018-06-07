package com.b00sti.notes.utils

import android.databinding.BindingAdapter
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.b00sti.notes.R
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by b00sti on 07.06.2018
 */
object StringUtils {

    @JvmStatic
    fun formatTag(text: String?): String {
        return when {
            !text.isNullOrEmpty() && !text?.get(0).toString().equals("#") -> "#$text"
            text != null -> text
            else -> ""
        }
    }

    @JvmStatic
    fun formatTime(timestamp: Long): String {
        return SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH).format(Date(timestamp))
    }

    @JvmStatic
    fun formatTagWithTitle(text: String?): String = ResUtils.getString(R.string.tag_info) + formatTag(text)

    @JvmStatic
    @BindingAdapter("addHashTag")
    fun addHashTag(view: EditText, text: String?) {
        view.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                view.removeTextChangedListener(this)
                val content = view.text.toString()
                if (content.isNotEmpty() && !"#".equals(content[0].toString(), true)) {
                    view.setText("#$content")
                    view.setSelection(view.text.toString().length)
                }
                view.addTextChangedListener(this)
            }
        })
    }
}