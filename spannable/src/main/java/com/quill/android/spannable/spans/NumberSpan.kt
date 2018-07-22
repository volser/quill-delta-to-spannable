package com.quill.android.spannable.spans

import android.graphics.Canvas
import android.graphics.Paint
import android.text.Spanned
import android.text.Layout
import android.text.style.LeadingMarginSpan


class NumberSpan(private val mNr: Int, private val mGapWidth: Int) : LeadingMarginSpan, QuillSpan<Boolean>, QuillParagraphSpan<Boolean> {

    private var mTextSize = 10f
    private var mWidth: Float = 0f

    override val value
        get() = true

    override fun getLeadingMargin(first: Boolean): Int {
        return Math.max(Math.round(mWidth + 2), mGapWidth)
    }

    override fun drawLeadingMargin(c: Canvas, p: Paint, x: Int, dir: Int, top: Int, baseline: Int, bottom: Int,
                                   text: CharSequence, start: Int, end: Int, first: Boolean, l: Layout) {

        val spanned = text as Spanned
        if (spanned.getSpanStart(this) == start) {
            // set paint
            val oldStyle = p.style
            val oldTextSize = p.textSize
            p.style = Paint.Style.FILL
            mTextSize = (baseline - top).toFloat()
            p.textSize = mTextSize
            mWidth = p.measureText(mNr.toString() + ".")

            // draw the number
            c.drawText("$mNr.", x.toFloat(), baseline.toFloat(), p)

            // restore paint
            p.style = oldStyle
            p.textSize = oldTextSize
        }
    }

    override fun createClone(): NumberSpan {
        return NumberSpan(mNr, mGapWidth)
    }

    companion object {

        const val ATTR_KEY = "list"
        const val ATTR_VALUE = "ordered"
    }

}