package com.husqvarna.movie_details.domain

import com.husqvarna.core.network.DispatcherProvider
import com.husqvarna.movie_details.repository.MovieDetailRepository
import com.husqvarna.movie_details.repository.models.MovieDetail
import kotlinx.coroutines.*

class MovieDetailInteractor(
    private val dispatcher: DispatcherProvider,
    private val repository: MovieDetailRepository
) : MovieDetailUseCase {
    override fun getDetail(
        movieId: Long,
        scope: CoroutineScope,
        onSuccess: (MovieDetail?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        scope.launch((dispatcher.io())) {
            try {
                val details = repository.getDetail(movieId)

                withContext(dispatcher.main()) {
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