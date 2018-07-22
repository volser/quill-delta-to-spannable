package com.quill.android.spannable.spans

import android.text.Layout


class AlignSpan
(align: Layout.Alignment) : android.text.style.AlignmentSpan.Standard(align), QuillSpan<Layout.Alignment>, QuillParagraphSpan<Layout.Alignment> {

    override val value: Layout.Alignment
        get() = alignment

    override fun createClone(): AlignSpan {
        return AlignSpan(value)
    }

    companion object {

        const val ATTR_KEY = "align"

        const val ALIGN_RIGHT = "right"
        const val ALIGN_LEFT = "left"
        const val ALIGN_CENTER = "center"
    }

}