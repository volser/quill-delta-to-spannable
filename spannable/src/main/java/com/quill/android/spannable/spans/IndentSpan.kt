package com.quill.android.spannable.spans

class IndentSpan(private val mIndentation: Int, private val mGapWidth: Int) : android.text.style.LeadingMarginSpan.Standard(mIndentation), QuillSpan<Int>, QuillParagraphSpan<Int> {

    /**
     * While the getLeadingMargin(boolean) is "officially" used when rendering the span
     * this method returns the indentation regardless of whether we want to render it or not.
     * Internally we always use this method.
     */
    override val value
        get() = mIndentation

    override fun getLeadingMargin(first: Boolean): Int {
        return mIndentation * mGapWidth
    }

    override fun createClone(): IndentSpan {
        return IndentSpan(mIndentation, mGapWidth)
    }

    companion object {

        const val ATTR_KEY = "indent"
    }

}