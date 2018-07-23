package com.quill.android.spannable.utils

import android.graphics.Color
import android.text.TextUtils


object ColorUtil {

    fun toHexString(intColor: Int): String {
        return String.format("#%06X", 0xFFFFFF and intColor)
    }


    @JvmOverloads
    fun parseColor(color: String, defaultColor: Int = Color.BLACK): Int {
        var color = color
        if (TextUtils.isEmpty(color))
            return defaultColor
        try {
            if (color.length == 4 && color.startsWith("#")) {
                val r = color.substring(1, 2)
                val g = color.substring(2, 3)
                val b = color.substring(3, 4)
                color = "#$r$r$g$g$b$b"

            }
            return Color.parseColor(color)
        } catch (ignored: Exception) {

        }

        return defaultColor
    }
}