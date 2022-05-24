package com.husqvarna.movie.ui.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieUI(
    val id: Long?,
    val title: String?,
    val position: Int,
    val rating: Double?,
    val imagePath: String?
) : Parcelable