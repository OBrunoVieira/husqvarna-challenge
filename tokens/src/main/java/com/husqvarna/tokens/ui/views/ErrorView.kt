package com.husqvarna.tokens.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.husqvarna.tokens.R
import com.husqvarna.tokens.databinding.ViewErrorBinding
import com.husqvarna.tokens.ui.extensions.compoundTopDrawable
import java.io.IOException

class ErrorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    companion object {
        const val INTERNET_ERROR = 1100
        const val GENERIC_ERROR = -1
    }

    private var errorType = GENERIC_ERROR
    private var binding: ViewErrorBinding? = null

    init {
        gravity = Gravity.CENTER
        orientation = VERTICAL

        binding = ViewErrorBinding.inflate(LayoutInflater.from(context), this)
    }

    fun throwable(throwable: Throwable?) = apply {
        this.errorType = if (throwable is IOException) INTERNET_ERROR else GENERIC_ERROR
    }

    fun errorType(errorType: Int) = apply {
        this.errorType = errorType
    }

    fun reload(listener: OnClickListener) = apply {
        binding?.errorButton?.setOnClickListener { listener.onClick(it) }
    }

    fun show() {
        visibility = VISIBLE
        build()
    }

    fun build() = binding?.run {
        when (errorType) {
            GENERIC_ERROR -> {
                errorTextViewTitle.compoundTopDrawable(R.drawable.vector_generic_error)
                errorTextViewTitle.setText(R.string.error_generic_title)
            }

            else -> {
                errorTextViewTitle.compoundTopDrawable(R.drawable.vector_no_internet)
                errorTextViewTitle.setText(R.string.error_internet_title)
            }
        }
    }
}