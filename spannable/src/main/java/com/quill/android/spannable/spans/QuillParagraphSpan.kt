package com.quill.android.spannable.spans

interface QuillParagraphSpan<V> {

    fun createClone(): QuillParagraphSpan<V>

}