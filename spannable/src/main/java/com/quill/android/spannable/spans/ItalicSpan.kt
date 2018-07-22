package com.quill.android.spannable.spans

import android.graphics.Typeface
import android.text.style.StyleSpan


class ItalicSpan : StyleSpan(Typeface.ITALIC), QuillSpan<Boolean> {

    override val value
        get() = true

    companion object {

        const val ATTR_KEY = "italic"
    }

}