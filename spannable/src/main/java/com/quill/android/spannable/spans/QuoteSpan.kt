package com.quill.android.spannable.spans

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.text.Layout
import android.text.style.LeadingMarginSpan
import android.text.style.LineHeightSpan
import android.text.style.StyleSpan
import com.quill.android.spannable.utils.SpanUtil


class QuoteSpan : StyleSpan(Typeface.ITALIC), LineHeightSpan, LeadingMarginSpan, QuillParagraphSpan<Boolean>, QuillSpan<Boolean> {
    private val mColor = 0xcccccc

    override val value
        get() = true

    override fun getLeadingMargin(first: Boolean): Int {
        return STRIPE_WIDTH + GAP_WIDTH
    }

    override fun drawLeadingMargin(c: Canvas, p: Paint, x: Int, dir: Int,
                                   top: Int, baseline: Int, bottom: Int,
                                   text: CharSequence, start: Int, end: Int,
                                   first: Boolean, layout: Layout) {
        val style = p.style
        val color = p.color
        p.style = Paint.Style.FILL
        p.color = mColor
        c.drawRect(x.toFloat(), top.toFloat(), (x + dir * STRIPE_WIDTH).toFloat(), bottom.toFloat(), p)
        p.style = style
        p.color = color
    }

    override fun createClone(): QuillParagraphSpan<Boolean> {
        return QuoteSpan()
    }

    override fun chooseHeight(text: CharSequence, start: Int, end: Int,
                              spanstartv: Int, v: Int, fm: Paint.FontMetricsInt) {
        SpanUtil.chooseHeightParagraphPadding(this, 20, text, start, end, fm)
    }

    companion object {

        const val ATTR_KEY = "blockquote"

        private const val STRIPE_WIDTH = 4
        private const val GAP_WIDTH = 20
    }


}