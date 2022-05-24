package com.husqvarna.movie_details.domain

import com.husqvarna.core.network.createApi
import com.husqvarna.movie_details.CoroutineTestRule
import com.husqvarna.movie_details.repository.MovieDetailRepository
import com.husqvarna.movie_details.repository.models.MovieDetail
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Rule
import org.junit.Test

import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment


@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class MovieDetailInteractorTest {
    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    private val mockWebServer = MockWebServer()

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `should get detail successfully`() =
        runTest(coroutinesTestRule.testDispatcher) {
            val server = MockWebServer().apply {
                enqueue(
                    MockResponse()
                        .setResponseCode(200)
                        .setBody(createMovieDetail().toString())
                )
            }

            val interactor = MovieDetailInteractor(
                coroutinesTestRule.testDispatcherProvider,
                MovieDetailRepository(
                    createApi(
                        RuntimeEnvironment.getApplication().applicationContext,
                        server.url("/").toString()
                    )
                )
            )

            interactor.getDetail(123, this,
                onSuccess = {
                    assert(it != null)
                })
        }

    @Test
    fun `should get detail with failure`() =
        runTest(coroutinesTestRule.testDispatcher) {
            val server = MockWebServer().apply {
                enqueue(
                    MockResponse()
                        .setResponseCode(400)
                )
            }

            val interactor = MovieDetailInteractor(
                coroutinesTestRule.testDispatcherProvider,
                MovieDetailRepository(
                    createApi(
                        RuntimeEnvironment.getApplication().applicationContext,
                        server.url("/").toString()
                    )
                )
            )

            interactor.getDetail(123, this,
                onError = {
                    assert(it is Exception)
                })
        }

    private fun createMovieDetail() = MovieDetail(0, "", "", "", 0.0, 0, null)
}