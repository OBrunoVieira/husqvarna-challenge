package com.husqvarna.movie.domain

import com.husqvarna.movie.repository.MovieRepository
import com.husqvarna.movie.repository.models.MoviesResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PopularMoviesInteractor(val repository: MovieRepository) : PopularMovieUseCase {

    override fun getTop10(
        scope: CoroutineScope,
        onSuccess: (MoviesResult?) -> Unit,
        onError: (String) -> Unit
    ) {
        scope.launch(Dispatchers.IO) {
            try {
                val popularMovies = repository.getPopularMovies()

                withContext(Dispatchers.Main) {
                    if (popularMovies.isSuccessful) {
                        onSuccess(popularMovies.body())
                    } else {
                        onError(popularMovies.message())
                    }
                }
            } catch (exception: Exception) {
                onError(exception.message.orEmpty())
            }
        }
    }
}