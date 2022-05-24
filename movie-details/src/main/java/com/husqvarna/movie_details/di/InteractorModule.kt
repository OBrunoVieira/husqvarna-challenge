package com.husqvarna.movie_details.di

import com.husqvarna.movie_details.domain.MovieDetailInteractor
import com.husqvarna.movie_details.domain.MovieDetailUseCase
import org.koin.dsl.module

val movieDetailInteractorModule = module {
    factory<MovieDetailUseCase> { MovieDetailInteractor(get()) }
}