package com.husqvarna.movie_details.di

import com.husqvarna.movie_details.viewmodel.MovieDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val movieDetailViewModel = module {
    viewModel { MovieDetailsViewModel(get()) }
}