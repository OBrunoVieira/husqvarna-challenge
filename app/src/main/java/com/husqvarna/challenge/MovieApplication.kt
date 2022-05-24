package com.husqvarna.challenge

import android.app.Application
import com.husqvarna.movie.di.moviesInteractorModule
import com.husqvarna.movie.di.moviesNetworkModule
import com.husqvarna.movie.di.moviesRepository
import com.husqvarna.movie.di.moviesViewModel
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
                moviesNetworkModule, moviesRepository, moviesInteractorModule, moviesViewModel
            )
        }
    }
}