package com.husqvarna.core.network

import android.content.Context
import com.husqvarna.core.BuildConfig.DEBUG
import com.husqvarna.core.BuildConfig.HOST_TMDB
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

inline fun <reified T> createApi(context: Context) = run {
    val retrofit = Retrofit.Builder()
        .baseUrl(HOST_TMDB)
        .client(buildOkHttpClient(DEBUG, context))
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    retrofit.create(T::class.java)
}

fun buildOkHttpClient(isDebug: Boolean, context: Context) = run {
    val loggingInterceptor =
        HttpLoggingInterceptor().apply {
            level =
                if (isDebug) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }

    OkHttpClient.Builder()
        .cache(Cache(context.cacheDir, 10 * 1024 * 1024))
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
}.build()

