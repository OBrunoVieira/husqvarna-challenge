package com.husqvarna.movie_details.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.husqvarna.movie_details.R
import com.husqvarna.movie_details.databinding.ViewItemDetailBinding

class ItemDetailView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    var binding: ViewItemDetailBinding? = null

    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER
        background = ContextCompat.getDrawable(context, R.drawable.selector_stroked_rectangle)

        ViewItemDetailBinding.inflate(LayoutInflater.from(context), this).also { binding = it }

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemDetailView)
        val drawable = typedArray.getDrawable(R.styleable.ItemDetailView_vid_icon)
        val textTitle = typedArray.getString(R.styleable.ItemDetailView_vid_title)
        val textValue = typedArray.getString(R.styleable.ItemDetailView_vid_value)

        try {
            binding?.itemDetailIcon?.background = drawable
            setTitle(textTitle)
            setValue(textValue)

        } finally {
            typedArray.recycle()
        }
    }

    fun setTitle(title: String?) = title.takeIf { !it.isNullOrBlank() }?.let {
        binding?.itemDetailTitle?.text = it
    }

    fun setValue(value: String?) = value.takeIf { !it.isNullOrBlank() }?.let {
        binding?.itemDetailValue?.text = it
    }
}