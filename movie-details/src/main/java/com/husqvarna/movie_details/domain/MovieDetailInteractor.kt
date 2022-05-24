package com.husqvarna.movie_details.domain

import com.husqvarna.movie_details.repository.MovieDetailRepository
import com.husqvarna.movie_details.repository.models.MovieDetail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailInteractor(private val repository: MovieDetailRepository) : MovieDetailUseCase {
    override fun getDetail(
        movieId: Long,
        scope: CoroutineScope,
        onSuccess: (MovieDetail?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        scope.launch((Dispatchers.IO)) {
            try {
                val details = repository.getDetail(movieId)

                withContext(Dispatchers.Main) {
                    if (details.isSuccessful) {
                        onSuccess(details.body())
                    } else {
                        onError(Exception(details.message()))
                    }
                }
            } catch (exception: Exception) {
                onError(exception)
            }
        }
    }
}