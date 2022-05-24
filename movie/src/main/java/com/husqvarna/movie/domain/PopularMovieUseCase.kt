package com.husqvarna.movie.domain

import com.husqvarna.movie.repository.models.MoviesResult
import kotlinx.coroutines.CoroutineScope

interface PopularMovieUseCase {
    fun getTop10(
        scope: CoroutineScope,
        onSuccess: (MoviesResult?) -> Unit,
        onError: (String) -> Unit
    )
}