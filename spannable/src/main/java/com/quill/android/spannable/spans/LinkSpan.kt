package com.quill.android.spannable.spans

import android.text.style.URLSpan
import android.view.View


class LinkSpan(url: String) : URLSpan(url), QuillSpan<String> {

    override val value: String?
        get() = url

    interface LinkSpanListener {
        fun onClick(linkSpan: LinkSpan): Boolean
    }

    override fun onClick(view: View) {
        if (view is LinkSpanListener) {
            if ((view as LinkSpanListener).onClick(this)) {
                return
            }
        }
        super.onClick(view)
    }

    companion object {

        const val ATTR_KEY = "link"
    }

}