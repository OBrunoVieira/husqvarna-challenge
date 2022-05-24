package com.husqvarna.challenge

import android.app.Application
import com.husqvarna.movie.di.*
import com.husqvarna.movie_details.di.movieDetailInteractorModule
import com.husqvarna.movie_details.di.movieDetailNetworkModule
import com.husqvarna.movie_details.di.movieDetailRepository
import com.husqvarna.movie_details.di.movieDetailViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MovieApplication)
            androidLogger()
            modules(
                //Movies Module
                moviesNetworkModule, moviesRepository, moviesInteractorModule, moviesViewModel,
                //Movie's detail
                movieDetailNetworkModule, movieDetailRepository, movieDetailInteractorModule,
                movieDetailViewModel
            )
        }
    }
}