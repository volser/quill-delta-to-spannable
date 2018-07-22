package com.quill.android.spannable.spans

import android.graphics.Typeface
import android.text.style.StyleSpan


class BoldSpan : StyleSpan(Typeface.BOLD), QuillSpan<Boolean> {

    override val value
        get() = true

    companion object {

        const val ATTR_KEY = "bold"
    }

}