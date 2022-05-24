package com.husqvarna.movie_details.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.husqvarna.core.viewmodel.DataSource
import com.husqvarna.movie_details.domain.MovieDetailUseCase
import com.husqvarna.movie_details.ui.models.MovieDetailUI

class MovieDetailsViewModel(private val movieDetailUseCase: MovieDetailUseCase) : ViewModel() {

    val liveDataMovieDetail = MutableLiveData<DataSource<MovieDetailUI>>()

    fun getMovieDetail(movieId: Long) {
        liveDataMovieDetail.postValue(DataSource.loading())

        movieDetailUseCase.getDetail(movieId, viewModelScope,
            onSuccess = {
                val data = MovieDetailUI(
                    it?.id,
                    it?.title,
                    it?.description,
                    it?.posterPath,
                    it?.runtime,
                    it?.rating,
                    it?.genres?.firstOrNull()?.name
                )
                liveDataMovieDetail.postValue(DataSource.success(data))
            },
            onError = {
                liveDataMovieDetail.postValue(DataSource.error(it))
            })
    }
}