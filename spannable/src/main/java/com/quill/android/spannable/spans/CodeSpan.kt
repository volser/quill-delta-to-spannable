package com.quill.android.spannable.spans

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.text.Layout
import android.text.style.LeadingMarginSpan
import android.text.style.LineBackgroundSpan
import android.text.style.LineHeightSpan
import android.text.style.TypefaceSpan
import com.quill.android.spannable.utils.SpanUtil


class CodeSpan : TypefaceSpan("monospace"), LineHeightSpan, LineBackgroundSpan, LeadingMarginSpan, QuillParagraphSpan<Boolean>, QuillSpan<Boolean> {

    override val value
        get() = true

    override fun createClone(): QuillParagraphSpan<Boolean> {
        return CodeSpan()
    }

    override fun chooseHeight(text: CharSequence, start: Int, end: Int,
                              spanstartv: Int, v: Int, fm: Paint.FontMetricsInt) {
        SpanUtil.chooseHeightParagraphPadding(this, 20, text, start, end, fm)
    }

    override fun drawBackground(c: Canvas, p: Paint,
                                left: Int, right: Int,
                                top: Int, baseline: Int, bottom: Int,
                                text: CharSequence, start: Int, end: Int,
                                lnum: Int) {
        val paintColor = p.color
        p.color = 0xf0f0f0
        c.drawRect(Rect(left, top, right, bottom), p)
        p.color = paintColor
    }

    override fun getLeadingMargin(b: Boolean): Int {
        return PADDING
    }

    override fun drawLeadingMargin(p0: Canvas?, p1: Paint?, p2: Int, p3: Int, p4: Int, p5: Int, p6: Int, p7: CharSequence?, p8: Int, p9: Int, p10: Boolean, p11: Layout?) {
    }

    companion object {

        const val ATTR_KEY = "code-block"
        private const val PADDING = 30
    }
}