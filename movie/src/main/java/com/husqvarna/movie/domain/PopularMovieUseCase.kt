package com.husqvarna.movie.domain

import com.husqvarna.movie.repository.models.Movie
import kotlinx.coroutines.CoroutineScope

interface PopularMovieUseCase {
    fun getTop10(
        scope: CoroutineScope,
        onSuccess: (List<Movie>?) -> Unit,
        onError: (String) -> Unit
    )
}