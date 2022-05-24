package com.husqvarna.movie_details.di

import com.husqvarna.core.network.createApi
import com.husqvarna.movie_details.network.MovieDetailDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val movieDetailNetworkModule = module {
    factory { createApi<MovieDetailDataSource>(androidContext()) }
}