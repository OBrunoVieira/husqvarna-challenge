package com.husqvarna.movie_details.ui.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetailUI(
    val id: Long?,
    val title: String?,
    val description: String?,
    val posterPath: String?,
    val runtime: Long?,
    val rating: Double?,
    val genre: String?
) : Parcelable