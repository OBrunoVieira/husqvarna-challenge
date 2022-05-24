package com.husqvarna.movie.di

import com.husqvarna.movie.domain.PopularMovieUseCase
import com.husqvarna.movie.domain.PopularMoviesInteractor
import org.koin.dsl.module

val moviesInteractorModule = module {
    factory<PopularMovieUseCase> { PopularMoviesInteractor(get()) }
}