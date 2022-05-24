package com.husqvarna.movie_details.network

import com.husqvarna.core.BuildConfig
import com.husqvarna.movie_details.repository.models.MovieDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDetailDataSource {
    @GET("movie/{movieId}")
    suspend fun getDetail(
        @Path("movieId") movieId:Long,
        @Query("api_key") value: String = BuildConfig.TMDB_API_KEY
    ): Response<MovieDetail>
}