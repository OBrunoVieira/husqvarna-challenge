package com.husqvarna.core.viewmodel

import org.junit.Test

class DataSourceTest {
    @Test
    fun `should build success source`() {
        val anyData = ""
        val dataSource = DataSource.success(anyData)

        assert(dataSource.dataState == DataState.SUCCESS)
        assert(dataSource.data == anyData)
    }

    @Test
    fun `should build error source`() {
        val anyThrowable = Throwable("any message")
        val dataSource = DataSource.error<Any>(anyThrowable)

        assert(dataSource.dataState == DataState.ERROR)
        assert(dataSource.throwable == anyThrowable)
    }

    @Test
    fun `should build loading source`() {
        val dataSource = DataSource.loading<Any>()
        assert(dataSource.dataState == DataState.LOADING)
    }
}