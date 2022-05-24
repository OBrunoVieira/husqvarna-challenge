package com.husqvarna.movie.di

import com.husqvarna.movie.repository.MovieRepository
import org.koin.dsl.module

val moviesRepository = module {
    factory { MovieRepository(get()) }
}