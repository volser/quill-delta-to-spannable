package com.quill.android.spannable.spans

import android.text.TextPaint
import android.graphics.Typeface
import android.text.style.StyleSpan


class HeaderSpan(size: Int) : StyleSpan(Typeface.BOLD), QuillSpan<Int>, QuillParagraphSpan<Int> {

    private val mSize: Int = when {
        size < 1 -> 1
        size > 6 -> 6
        else -> size
    }

    override val value
        get() = mSize

    private val proportion: Float
        get() = HEADER_SIZES[mSize - 1]

    override fun updateDrawState(ds: TextPaint) {
        ds.textSize = ds.textSize * proportion
    }

    override fun updateMeasureState(ds: TextPaint) {
        ds.textSize = ds.textSize * proportion
    }

    override fun createClone(): QuillParagraphSpan<Int> {
        return HeaderSpan(mSize)
    }

    companion object {

        private val HEADER_SIZES = floatArrayOf(1.5f, 1.4f, 1.3f, 1.2f, 1.1f, 1f)

        const val ATTR_KEY = "header"
    }
}