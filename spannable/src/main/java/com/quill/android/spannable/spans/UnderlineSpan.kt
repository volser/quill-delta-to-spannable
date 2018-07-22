package com.quill.android.spannable.spans

class UnderlineSpan : android.text.style.UnderlineSpan(), QuillSpan<Boolean> {

    override val value
        get() = true

    companion object {

        const val ATTR_KEY = "underline"
    }

}