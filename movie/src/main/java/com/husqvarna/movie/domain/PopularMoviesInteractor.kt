package com.husqvarna.movie.domain

import com.husqvarna.movie.repository.MovieRepository
import com.husqvarna.movie.repository.models.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PopularMoviesInteractor(private val repository: MovieRepository) : PopularMovieUseCase {

    override fun getTop10(
        scope: CoroutineScope,
        onSuccess: (List<Movie>?) -> Unit,
        onError: (String) -> Unit
    ) {
        scope.launch(Dispatchers.IO) {
            try {
                val popularMovies = repository.getPopularMovies()

                withContext(Dispatchers.Main) {
                    if (popularMovies.isSuccessful) {
                        val movies =
                            popularMovies.body()?.movies
                                ?.sortedByDescending { it.voteAverage }
                                ?.take(10)

                        onSuccess(movies)
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