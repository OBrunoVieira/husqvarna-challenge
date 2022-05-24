package com.husqvarna.movie_details.repository.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetail(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("overview")
    val description: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("vote_average")
    val rating: Double?,
    @SerializedName("runtime")
    val runtime: Long?,
    @SerializedName("genres")
    val genres: List<MovieGenre>?
) : Parcelable