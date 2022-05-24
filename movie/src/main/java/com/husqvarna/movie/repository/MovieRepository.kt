package com.husqvarna.movie.repository

import com.husqvarna.movie.network.MoviesDataSource

class MovieRepository(private val dataSource: MoviesDataSource) {
    suspend fun getPopularMovies() = dataSource.getPopularMovies()
}