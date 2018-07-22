package com.quill.android.spannable.spans

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.text.Spanned
import android.text.Layout
import android.text.style.LeadingMarginSpan


class BulletSpan

(private val mGapWidth: Int) : LeadingMarginSpan, QuillSpan<Boolean>, QuillParagraphSpan<Boolean> {

    override val value
        get() = true

    override fun getLeadingMargin(first: Boolean): Int {
        return 2 * BULLET_RADIUS + mGapWidth
    }

    override fun drawLeadingMargin(c: Canvas, p: Paint, x: Int, dir: Int,
                                   top: Int, baseline: Int, bottom: Int,
                                   text: CharSequence, start: Int, end: Int,
                                   first: Boolean, l: Layout) {
        if ((text as Spanned).getSpanStart(this) == start) {
            val style = p.style

            p.style = Paint.Style.FILL
            if (c.isHardwareAccelerated) {
                if (sBulletPath == null) {
                    sBulletPath = Path()
                    // Bullet is slightly better to avoid aliasing artifacts on mdpi devices.
                    sBulletPath!!.addCircle(0.0f, 0.0f, 1.2f * BULLET_RADIUS, Path.Direction.CW)
                }
                c.save()
                c.translate((x + dir * BULLET_RADIUS).toFloat(), (top + bottom) / 2.0f)
                c.drawPath(sBulletPath, p)
                c.restore()
            } else {
                c.drawCircle((x + dir * BULLET_RADIUS).toFloat(), (top + bottom) / 2.0f, BULLET_RADIUS.toFloat(), p)
            }
            p.style = style
        }
    }

    override fun createClone(): BulletSpan {
        return BulletSpan(mGapWidth)
    }

    companion object {

        const val ATTR_KEY = "list"
        const val ATTR_VALUE = "bullet"

        private var sBulletPath: Path? = null
        private const val BULLET_RADIUS = 3
        const val STANDARD_GAP_WIDTH = 2
    }

}