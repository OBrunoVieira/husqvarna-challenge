package com.husqvarna.movie.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.husqvarna.core.viewmodel.DataSource
import com.husqvarna.movie.domain.PopularMovieUseCase
import com.husqvarna.movie.repository.models.Movie
import com.husqvarna.movie.ui.models.MovieUI

class MovieViewModel(internal val popularMovieUseCase: PopularMovieUseCase) : ViewModel() {

    val liveDataPopularMovies = MutableLiveData<DataSource<List<MovieUI>>>()

    fun getTop10PopularMovies() {
        liveDataPopularMovies.postValue(DataSource.loading())

        popularMovieUseCase.getTop10(viewModelScope,
            onSuccess = { movies ->
                val data = transformToMovieUI(movies)
                liveDataPopularMovies.postValue(DataSource.success(data))
            },
            onError = {
                liveDataPopularMovies.postValue(DataSource.error(it))
            })
    }

    internal fun transformToMovieUI(list: List<Movie>?) =
        list?.mapIndexed { index, item ->
            MovieUI(
                item.id,
                item.title,
                index,
                item.voteAverage,
                item.posterPath
            )
        }
}