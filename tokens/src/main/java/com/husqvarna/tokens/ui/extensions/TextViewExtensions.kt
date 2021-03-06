package com.husqvarna.tokens.ui.extensions

import android.widget.TextView
import androidx.annotation.DrawableRes

fun TextView.compoundTopDrawable(@DrawableRes drawableRes: Int) {
    setCompoundDrawablesRelativeWithIntrinsicBounds(0, drawableRes, 0, 0)
}