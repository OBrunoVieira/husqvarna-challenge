package com.husqvarna.movie.domain

import com.husqvarna.core.network.DispatcherProvider
import com.husqvarna.movie.repository.MovieRepository
import com.husqvarna.movie.repository.models.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PopularMoviesInteractor(
    private val dispatcher: DispatcherProvider,
    private val repository: MovieRepository
) : PopularMovieUseCase {

    override fun getTop10(
        scope: CoroutineScope,
        onSuccess: (List<Movie>?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        scope.launch(dispatcher.io()) {
            try {
                val popularMovies = repository.getPopularMovies()

                withContext(dispatcher.main()) {
                    if (popularMovies.isSuccessful) {
                        val movies = sortMoviesByDescending(popularMovies.body()?.movies)
                        onSuccess(movies)
                    } else {
                        onError(Exception(popularMovies.message()))
                    }
                }
            } catch (exception: Exception) {
                onError(exception)
            }
        }
    }

    internal fun sortMoviesByDescending(movies: List<Movie>?) =
        movies?.sortedByDescending { it.voteAverage }
            ?.take(10)
}