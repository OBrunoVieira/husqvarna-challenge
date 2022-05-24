package com.husqvarna.movie.di

import com.husqvarna.movie.viewmodel.MovieViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moviesViewModel = module {
    viewModel { MovieViewModel(get()) }
}