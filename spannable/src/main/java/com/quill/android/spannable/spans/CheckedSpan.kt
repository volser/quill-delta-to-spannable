package com.quill.android.spannable.spans

import android.graphics.Canvas
import android.graphics.Paint
import android.text.Layout
import android.text.Spanned
import android.text.style.LeadingMarginSpan


class CheckedSpan(private val mGapWidth: Int) : LeadingMarginSpan, QuillSpan<Boolean>, QuillParagraphSpan<Boolean> {

    override val value
        get() = true

    override fun getLeadingMargin(first: Boolean): Int {
        return SIZE + mGapWidth
    }

    override fun drawLeadingMargin(c: Canvas, p: Paint, x: Int, dir: Int,
                                   top: Int, baseline: Int, bottom: Int,
                                   text: CharSequence, start: Int, end: Int,
                                   first: Boolean, l: Layout) {
        if ((text as Spanned).getSpanStart(this) == start) {
            val style = p.style

            val cx = (x + 5).toFloat()
            val cy = (top + 8).toFloat()
            val checkGap = 7f
            val points = floatArrayOf(cx, cy, cx + SIZE, cy, cx + SIZE, cy, cx + SIZE, cy + SIZE, cx + SIZE, cy + SIZE, cx, cy + SIZE, cx, cy + SIZE, cx, cy,

                    cx + checkGap, cy + SIZE / 2, cx + SIZE / 3, cy + SIZE - checkGap, cx + SIZE / 3, cy + SIZE - checkGap, cx + SIZE - checkGap, cy + checkGap)
            p.strokeWidth = 3f
            p.strokeCap = Paint.Cap.ROUND
            c.drawLines(points, p)

            p.style = style
        }
    }

    override fun createClone(): CheckedSpan {
        return CheckedSpan(mGapWidth)
    }

    companion object {

        const val ATTR_KEY = "list"
        const val ATTR_VALUE = "checked"

        private const val SIZE = 30
    }

}