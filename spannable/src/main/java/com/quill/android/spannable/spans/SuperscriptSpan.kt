package com.quill.android.spannable.spans


class SuperscriptSpan : android.text.style.SuperscriptSpan(), QuillSpan<Boolean> {

    override val value
        get() = true

    companion object {

        const val ATTR_KEY = "script"
        const val ATTR_VALUE = "super"
    }

}