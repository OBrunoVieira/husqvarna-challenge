package com.husqvarna.movie.repository.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    @SerializedName("id")
    val id : Long?,
    @SerializedName("adult")
    val adult : Boolean?,
    @SerializedName("backdrop_path")
    val backdropPath : String?,
    @SerializedName("poster_path")
    val posterPath : String?,
    @SerializedName("title")
    val title : String?,
    @SerializedName("overview")
    val description : String?,
    @SerializedName("release_date")
    val releaseDate : String?,
    @SerializedName("vote_average")
    val voteAverage : Double?,
) : Parcelable