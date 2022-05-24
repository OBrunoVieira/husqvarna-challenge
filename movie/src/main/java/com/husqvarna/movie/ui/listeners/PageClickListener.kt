package com.husqvarna.movie.ui.listeners

import com.husqvarna.movie.ui.models.MovieUI

interface PageClickListener {
    fun onPageClicked(movie: MovieUI?)
}