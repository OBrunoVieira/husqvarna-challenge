package com.husqvarna.movie.di

import android.content.Context
import com.husqvarna.core.network.buildOkHttpClient
import com.husqvarna.movie.BuildConfig.DEBUG
import com.husqvarna.movie.BuildConfig.HOST_TMDB
import com.husqvarna.movie.network.MoviesDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val moviesNetworkModule = module {
    factory { createMoviesApi(androidContext()) }
}

private fun createMoviesApi(context: Context) = run {
    val retrofit = Retrofit.Builder()
        .baseUrl(HOST_TMDB)
        .client(buildOkHttpClient(DEBUG, context))
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    retrofit.create(MoviesDataSource::class.java)
}