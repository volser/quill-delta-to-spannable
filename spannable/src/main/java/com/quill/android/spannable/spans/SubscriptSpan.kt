package com.quill.android.spannable.spans


class SubscriptSpan : android.text.style.SubscriptSpan(), QuillSpan<Boolean> {

    override val value
        get() = true

    companion object {

        const val ATTR_KEY = "script"
        const val ATTR_VALUE = "sub"
    }

}