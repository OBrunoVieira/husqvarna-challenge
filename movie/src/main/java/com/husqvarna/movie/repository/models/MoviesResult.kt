package com.husqvarna.movie.repository.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MoviesResult(
    @SerializedName("results")
    val movies: List<Movie>?
) : Parcelable