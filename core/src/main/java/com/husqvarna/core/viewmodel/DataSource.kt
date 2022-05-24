package com.husqvarna.core.viewmodel

data class DataSource<T>(
    val dataState: DataState,
    val data: T? = null,
    val throwable: Throwable? = null
) {
    companion object {
        fun <T> success(result: T?) = DataSource(DataState.SUCCESS, result)

        fun <T> error() = DataSource<T>(DataState.ERROR)

        fun <T> loading() = DataSource<T>(DataState.LOADING)
    }
}
