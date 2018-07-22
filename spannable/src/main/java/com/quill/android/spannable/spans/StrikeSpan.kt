package com.quill.android.spannable.spans


class StrikeSpan : android.text.style.StrikethroughSpan(), QuillSpan<Boolean> {

    override val value
        get() = true

    companion object {

        const val ATTR_KEY = "strike"
    }

}