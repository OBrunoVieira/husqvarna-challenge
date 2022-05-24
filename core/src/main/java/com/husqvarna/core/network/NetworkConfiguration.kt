package com.husqvarna.core.network

import android.content.Context
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

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

