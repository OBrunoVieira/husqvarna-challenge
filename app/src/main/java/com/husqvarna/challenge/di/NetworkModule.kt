package com.husqvarna.challenge.di

import android.content.Context
import com.husqvarna.challenge.BuildConfig.*
import com.husqvarna.challenge.extensions.isNetworkConnected
import com.husqvarna.challenge.network.Headers
import com.husqvarna.challenge.network.MoviesDataSource
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    factory { createMoviesApi(androidContext()) }
}

private fun createMoviesApi(context: Context) = run {
    val retrofit = Retrofit.Builder()
        .baseUrl(HOST_TMDB)
        .client(buildOkHttpClient(context))
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    retrofit.create(MoviesDataSource::class.java)
}

private fun buildOkHttpClient(context: Context) = run {
    val loggingInterceptor =
        HttpLoggingInterceptor().apply {
            level =
                if (DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }

    OkHttpClient.Builder()
        .cache(Cache(context.cacheDir, 10 * 1024 * 1024))
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .addInterceptor(createMoviesCachePolicy(context))
}.build()

private fun createMoviesCachePolicy(context: Context) = object : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (context.isNetworkConnected()) {
            val request = chain.request()
                .newBuilder()
                .addHeader(Headers.API_KEY.value, TMDB_API_KEY)
                .build()

//            val cacheControl = CacheControl.Builder()
//                .maxAge(2, TimeUnit.HOURS)
//                .build()
//
            return chain.proceed(request)
                .newBuilder()
//                .removeHeader("Pragma")
//                .removeHeader("Cache-Control")
//                .addHeader("Cache-Control", cacheControl.toString())
                .build()
        }

        return chain.proceed(chain.request())
    }
}