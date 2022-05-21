package com.husqvarna.challenge

import android.app.Application
import com.husqvarna.challenge.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MovieApplication)
            androidLogger()
            modules(networkModule)
        }

    }
}