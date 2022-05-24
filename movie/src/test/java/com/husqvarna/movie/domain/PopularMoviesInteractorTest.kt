package com.husqvarna.movie.domain

import com.husqvarna.core.CoroutineTestRule
import com.husqvarna.core.network.createApi
import com.husqvarna.movie.repository.MovieRepository
import com.husqvarna.movie.repository.models.Movie
import com.husqvarna.movie.repository.models.MoviesResult
import io.mockk.mockk
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
class PopularMoviesInteractorTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    private val mockWebServer = MockWebServer()

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `should get top 10 successfully`() =
        runTest(coroutinesTestRule.testDispatcher) {
            val server = MockWebServer().apply {
                enqueue(
                    MockResponse()
                        .setResponseCode(200)
                        .setBody(createMovieResult().toString())
                )
            }

            val interactor = PopularMoviesInteractor(
                coroutinesTestRule.testDispatcherProvider,
                MovieRepository(
                    createApi(
                        RuntimeEnvironment.getApplication().applicationContext,
                        server.url("/").toString()
                    )
                )
            )

            interactor.getTop10(this,
                onSuccess = {
                    assert(it?.isNotEmpty() == true)
                })
        }

    @Test
    fun `should get top 10 with failure`() =
        runTest(coroutinesTestRule.testDispatcher) {
            val server = MockWebServer().apply {
                enqueue(
                    MockResponse()
                        .setResponseCode(400)
                )
            }

            val interactor = PopularMoviesInteractor(
                coroutinesTestRule.testDispatcherProvider,
                MovieRepository(
                    createApi(
                        RuntimeEnvironment.getApplication().applicationContext,
                        server.url("/").toString()
                    )
                )
            )

            interactor.getTop10(this,
                onError = {
                    assert(it is Exception)
                })
        }

    @Test
    fun `should sort movies by descending votes`() =
        runTest(coroutinesTestRule.testDispatcher) {
            val interactor = PopularMoviesInteractor(
                coroutinesTestRule.testDispatcherProvider,
                mockk()
            )

            val mostVotedMovie = createMovie(9.0)
            val list = listOf(
                createMovie(8.0),
                createMovie(6.0),
                mostVotedMovie,
            )

            assert(interactor.sortMoviesByDescending(list)?.firstOrNull() == mostVotedMovie)
        }

    private fun createMovie(average: Double = 0.0) = Movie(0, false, "", "", "", "", "", average)

    private fun createMovieResult() = MoviesResult(listOf(createMovie(), createMovie()))
}