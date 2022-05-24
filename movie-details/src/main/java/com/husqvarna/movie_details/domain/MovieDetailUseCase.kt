package com.husqvarna.movie_details.domain

import com.husqvarna.movie_details.repository.models.MovieDetail
import kotlinx.coroutines.CoroutineScope

interface MovieDetailUseCase {
    fun getDetail(
        movieId: Long,
        scope: CoroutineScope,
        onSuccess: (MovieDetail?) -> Unit,
        onError: (Throwable) -> Unit
    )
}