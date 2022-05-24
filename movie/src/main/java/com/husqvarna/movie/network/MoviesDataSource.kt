package com.husqvarna.movie.network

import com.husqvarna.movie.BuildConfig
import com.husqvarna.movie.repository.models.MoviesResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesDataSource {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") value: String = BuildConfig.TMDB_API_KEY
    ): Response<MoviesResult>
}