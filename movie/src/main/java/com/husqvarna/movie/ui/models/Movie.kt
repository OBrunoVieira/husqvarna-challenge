package com.husqvarna.movie.ui.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(val title:String?, val position:Int, val rating:Double?) : Parcelable