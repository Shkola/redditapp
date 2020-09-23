package com.reddit.extension

import android.widget.TextView
import androidx.core.view.isVisible

fun TextView.setTextOrHide(value: String?) {
    text = value
    isVisible = !value.isNullOrEmpty()
}