package com.husqvarna.movie.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.husqvarna.core.viewmodel.DataSource
import com.husqvarna.movie.domain.PopularMovieUseCase
import com.husqvarna.movie.ui.models.MovieUI

class MovieViewModel(val popularMovieUseCase: PopularMovieUseCase) : ViewModel() {

    val liveDataPopularMovies = MutableLiveData<DataSource<List<MovieUI>>>()

    fun getTop10PopularMovies() {
        liveDataPopularMovies.postValue(DataSource.loading())

        popularMovieUseCase.getTop10(viewModelScope,
            onSuccess = { movies ->
                val data = movies?.mapIndexed { index, item ->
                    MovieUI(
                        item.id,
                        item.title,
                        index,
                        item.voteAverage,
                        item.posterPath
                    )
                }

                liveDataPopularMovies.postValue(DataSource.success(data))
            },
            onError = {
                liveDataPopularMovies.postValue(DataSource.error())
            })
    }

}