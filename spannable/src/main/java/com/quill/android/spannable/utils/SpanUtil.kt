package com.quill.android.spannable.utils

import android.graphics.Paint
import android.text.SpannableStringBuilder
import android.text.Spanned


object SpanUtil {

    fun chooseHeightParagraphPadding(span: Any, padding: Int, text: CharSequence, start: Int, end: Int,
                                     fm: Paint.FontMetricsInt) {
        val spanned = text as Spanned
        val st = spanned.getSpanStart(span)
        val en = spanned.getSpanEnd(span)
        val isFirst = start == st && (start == 0 || spanned.getSpans(start - 1, start - 1, span.javaClass).isEmpty())
        val isLast = end == en && (end == spanned.length - 1 || spanned.getSpans(end + 1, end + 1, span.javaClass).isEmpty())

        if (isFirst) {
            fm.ascent -= padding
            fm.top -= padding
        }
        /*if (isLast) {
            fm.descent += padding;
            fm.bottom += padding;
        }*/
    }

    fun trimSpannable(spannable: SpannableStringBuilder?): SpannableStringBuilder? {
        if (spannable == null)
            return null

        var trimStart = 0
        var trimEnd = 0

        var text = spannable.toString()

        while (text.isNotEmpty() && text.startsWith("\n")) {
            text = text.substring(1)
            trimStart += 1
        }

        while (text.isNotEmpty() && text.endsWith("\n")) {
            text = text.substring(0, text.length - 1)
            trimEnd += 1
        }

        return spannable.delete(0, trimStart).delete(spannable.length - trimEnd, spannable.length)
    }
}