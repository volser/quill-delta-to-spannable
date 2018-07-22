package com.quill.android.spannable.spans


class BackgroundColorSpan(color: Int) : android.text.style.BackgroundColorSpan(color), QuillSpan<Int> {
    override val value
        get() = backgroundColor


    companion object {

        const val ATTR_KEY = "background"
    }

}