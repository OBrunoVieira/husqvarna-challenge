package com.husqvarna.movie_details.di

import com.husqvarna.movie_details.repository.MovieDetailRepository
import org.koin.dsl.module

val movieDetailRepository = module {
    factory { MovieDetailRepository(get()) }
}