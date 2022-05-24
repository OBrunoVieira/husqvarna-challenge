package com.husqvarna.movie.di

import com.husqvarna.core.network.createApi
import com.husqvarna.movie.network.MoviesDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val moviesNetworkModule = module {
    factory { createApi<MoviesDataSource>(androidContext()) }
}