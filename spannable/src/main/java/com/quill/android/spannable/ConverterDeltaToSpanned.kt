package com.quill.android.spannable

import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.URLSpan

import android.text.util.Linkify
import android.text.Layout
import android.text.TextUtils
import com.quill.android.delta.Delta
import com.quill.android.delta.Op
import com.quill.android.spannable.spans.*
import com.quill.android.spannable.utils.ColorUtil


class ConverterDeltaToSpanned(private var mOpHandler: OpHandler? = null) {


    private var mResult: SpannableStringBuilder? = null
    private var mCursor: Int = 0

    fun convert(delta: Delta, linkifyMask: Int = 0): Spanned? {
        return convert(delta, true, linkifyMask)
    }

    fun convert(delta: Delta, trim: Boolean, linkifyMask: Int): Spanned? {

        mResult = SpannableStringBuilder()

        //removeTrailingLineBreaks();
        var lastEndLine = 0
        var numberSpanValue = 0
        var numberSpanWasLast = false
        mCursor = 0
        for (op in delta.ops) {
            var insertText = op.insert as? String
            /*if (TextUtils.isEmpty(insertText) && op.getInsertObject() != null) {
                if (mOpHandler != null) {
                    insertText = mOpHandler.getText(op);
                }
            }*/
            if (mOpHandler != null) {
                insertText = mOpHandler!!.getText(op)
            }

            if (!insertText.isNullOrEmpty()) {

                //Timber.d("insertText %s", insertText);
                mResult!!.append(insertText)
                val insertStart = mCursor
                mCursor += +insertText!!.length
                val insertEnd = mCursor

                if (mOpHandler != null) {
                    mOpHandler!!.handleOp(this, op, null, insertStart, insertEnd)
                }

                //attributes
                if (op.attributes != null) {
                    for (attrKey in op.attributes!!.keys) {

                        val value = op.attributes!![attrKey]

                        //Timber.d("Attribute %s:%s", attrKey, value)

                        //inline styles
                        var span: QuillSpan<*>? = getInlineStyleSpan(attrKey, value!!)
                        if (span != null) {
                            setInlineSpan(span, insertStart, insertEnd)
                        } else if (AlignSpan.ATTR_KEY == attrKey) {
                            val alignString = value as String
                            if (!TextUtils.isEmpty(alignString)) {

                                var align: Layout.Alignment? = null
                                if (AlignSpan.ALIGN_RIGHT == alignString) {
                                    align = Layout.Alignment.ALIGN_OPPOSITE
                                } else if (AlignSpan.ALIGN_CENTER == alignString) {
                                    align = Layout.Alignment.ALIGN_CENTER
                                } else if (AlignSpan.ALIGN_LEFT == alignString) {
                                    align = Layout.Alignment.ALIGN_NORMAL
                                }
                                if (align != null) {
                                    appendNewLineIfNeed(insertEnd)
                                    span = AlignSpan(align)
                                    setBlockSpan(span, lastEndLine, insertEnd)
                                }
                            }
                        } else if (HeaderSpan.ATTR_KEY == attrKey) {
                            appendNewLineIfNeed(insertEnd)
                            val num = value as Number
                            span = HeaderSpan(num.toInt())
                            setBlockSpan(span, lastEndLine, insertEnd)
                        } else if (BulletSpan.ATTR_KEY == attrKey && BulletSpan.ATTR_VALUE == value) {
                            appendNewLineIfNeed(insertEnd)
                            span = BulletSpan(30)
                            setBlockSpan(span, lastEndLine, insertEnd)
                        } else if (NumberSpan.ATTR_KEY == attrKey && NumberSpan.ATTR_VALUE == value) {
                            appendNewLineIfNeed(insertEnd)
                            numberSpanValue++
                            numberSpanWasLast = true
                            span = NumberSpan(numberSpanValue, 30)
                            setBlockSpan(span, lastEndLine, insertEnd)
                            //ToDo empty lines /n/n
                        } else if (CheckedSpan.ATTR_KEY == attrKey && CheckedSpan.ATTR_VALUE == value) {
                            appendNewLineIfNeed(insertEnd)
                            span = CheckedSpan(30)
                            setBlockSpan(span, lastEndLine, insertEnd)
                        } else if (UncheckedSpan.ATTR_KEY == attrKey && UncheckedSpan.ATTR_VALUE == value) {
                            appendNewLineIfNeed(insertEnd)
                            span = UncheckedSpan(30)
                            setBlockSpan(span, lastEndLine, insertEnd)
                        } else if (IndentSpan.ATTR_KEY == attrKey) {
                            appendNewLineIfNeed(insertEnd)
                            val num = value as Number
                            span = IndentSpan(num.toInt(), 40)
                            setBlockSpan(span, lastEndLine, insertEnd)
                        } else if (QuoteSpan.ATTR_KEY.equals(attrKey)) {
                            appendNewLineIfNeed(insertEnd)
                            span = QuoteSpan()
                            setBlockSpan(span, lastEndLine, insertEnd)
                        } else if (CodeSpan.ATTR_KEY == attrKey) {
                            appendNewLineIfNeed(insertEnd)
                            span = CodeSpan()
                            setBlockSpan(span, lastEndLine, insertEnd)
                        }//block styles
                    }
                }

                val newLinePos = insertText.lastIndexOf('\n')
                if (newLinePos >= 0) {
                    lastEndLine = mResult!!.length - insertText.length + newLinePos + 1
                    if (!numberSpanWasLast) {
                        numberSpanValue = 0
                    }
                    numberSpanWasLast = false
                }
            }
        }

        // linkify
        if (linkifyMask > 0) {
            Linkify.addLinks(mResult!!, linkifyMask)
        }

        // replace all TemporarySpans by the "real" spans
        for (span in mResult!!.getSpans(0, mResult!!.length, TemporaryInlineSpan::class.java)) {
            span.swapIn(mResult!!)
        }

        for (span in mResult!!.getSpans(0, mResult!!.length, TemporaryBlockSpan::class.java)) {
            span.swapIn(mResult!!)
        }

        if (trim) {
            removeTrailingLineBreaks()
        }
        return mResult
    }

    private fun appendNewLineIfNeed(position: Int) {
        //ToDo check this
        /*if (mResult.charAt(position - 1) != '\n') {
            // yes we need that linefeed, or we will get crashes
            mResult.append('\n');
        }*/
    }

    private fun getInlineStyleSpan(attrKey: String, value: Any): QuillSpan<*>? {

        if (BoldSpan.ATTR_KEY == attrKey && value as Boolean) {
            return BoldSpan()
        } else if (ItalicSpan.ATTR_KEY == attrKey && value as Boolean) {
            return ItalicSpan()
        } else if (UnderlineSpan.ATTR_KEY == attrKey && value as Boolean) {
            return UnderlineSpan()
        } else if (StrikeSpan.ATTR_KEY == attrKey && value as Boolean) {
            return StrikeSpan()
        } else if (SuperscriptSpan.ATTR_KEY == attrKey && SuperscriptSpan.ATTR_VALUE == value) {
            return SuperscriptSpan()
        } else if (SubscriptSpan.ATTR_KEY == attrKey && SubscriptSpan.ATTR_VALUE == value) {
            return SubscriptSpan()
        } else if (LinkSpan.ATTR_KEY == attrKey) {
            return LinkSpan(value as String)
        } else if (ForegroundColorSpan.ATTR_KEY == attrKey) {
            return ForegroundColorSpan(ColorUtil.parseColor(value as String, Color.BLACK))
        } else if (BackgroundColorSpan.ATTR_KEY == attrKey) {
            return BackgroundColorSpan(ColorUtil.parseColor(value as String, Color.TRANSPARENT))
        }

        return null
    }

    fun setInlineSpan(what: Any, start: Int, end: Int) {
        setSpan(TemporaryInlineSpan(what), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    fun setBlockSpan(what: Any, start: Int, end: Int) {
        setSpan(TemporaryBlockSpan(what), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    private fun setSpan(what: Any, start: Int, end: Int, flags: Int) {
        mResult!!.setSpan(what, start, end, flags)
    }

    fun setOpHandler(mOpHandler: OpHandler) {
        this.mOpHandler = mOpHandler
    }

    private class TemporaryInlineSpan internal constructor(internal var mSpan: Any) {

        internal fun swapIn(builder: SpannableStringBuilder) {
            val start = builder.getSpanStart(this)
            val end = builder.getSpanEnd(this)
            builder.removeSpan(this)
            if (start in 0..(end - 1) && end <= builder.length) {
                if (mSpan is LinkSpan) {
                    val urls = builder.getSpans(start, end, URLSpan::class.java)
                    for (url in urls) {
                        builder.removeSpan(url)
                    }
                }
                builder.setSpan(mSpan, start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
            }
        }

        companion object {

            fun apply(builder: SpannableStringBuilder, span: Any): TemporaryInlineSpan {
                val temporaryInlineSpan = TemporaryInlineSpan(span)
                val start = builder.getSpanStart(span)
                val end = builder.getSpanEnd(span)
                builder.removeSpan(span)
                if (start in 0..(end - 1) && end <= builder.length) {
                    builder.setSpan(temporaryInlineSpan, start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
                }
                return temporaryInlineSpan
            }
        }
    }

    private class TemporaryBlockSpan internal constructor(internal var mSpan: Any) {

        internal fun swapIn(builder: SpannableStringBuilder) {
            val start = builder.getSpanStart(this)
            val end = builder.getSpanEnd(this)
            builder.removeSpan(this)
            if (start in 0..(end - 1) && end <= builder.length) {
                builder.setSpan(mSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            }
        }
    }

    private fun removeTrailingLineBreaks() {
        var end = mResult!!.length
        while (end > 0 && mResult!![end - 1] == '\n') {
            end--
        }
        if (end < mResult!!.length) {
            mResult = SpannableStringBuilder.valueOf(mResult!!.subSequence(0, end))
        }
    }

    interface OpHandler {
        fun handleOp(converter: ConverterDeltaToSpanned, op: Op, attr: String?, insertStart: Int, insertEnd: Int)
        fun getText(op: Op): String
    }
}