package com.husqvarna.movie_details.repository

import com.husqvarna.movie_details.network.MovieDetailDataSource

class MovieDetailRepository(private val dataSource: MovieDetailDataSource) {
    suspend fun getDetail(movieId: Long) = dataSource.getDetail(movieId)
}