package com.quill.android.spannable.spans

class ForegroundColorSpan(color: Int) : android.text.style.ForegroundColorSpan(color), QuillSpan<Int> {

    override val value
        get() = foregroundColor

    companion object {

        const val ATTR_KEY = "color"
    }

}