package com.husqvarna.movie.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.husqvarna.core.viewmodel.DataState
import com.husqvarna.movie.repository.models.Movie
import com.husqvarna.movie.ui.models.MovieUI
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import org.junit.Rule
import org.junit.Test

class MovieViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val viewModel = MovieViewModel(mockk(relaxed = true))

    @Test
    fun `should get top 10 popular movies successfully`() {

        val movies = arrayListOf(createMovie(), createMovie())

        every {
            viewModel.popularMovieUseCase.getTop10(any(), any(), any())
        } answers {
            secondArg<(List<Movie>?) -> Unit>().invoke(movies)
        }

        viewModel.getTop10PopularMovies()

        assert(viewModel.liveDataPopularMovies.value?.dataState == DataState.SUCCESS)
        assert(viewModel.liveDataPopularMovies.value?.data == viewModel.transformToMovieUI(movies))
    }

    @Test
    fun `should get top 10 popular movies with failure`() {
        val throwable = Throwable()

        every {
            viewModel.popularMovieUseCase.getTop10(any(), any(), any())
        } answers {
            thirdArg<(Throwable) -> Unit>().invoke(throwable)
        }

        viewModel.getTop10PopularMovies()

        assert(viewModel.liveDataPopularMovies.value?.dataState == DataState.ERROR)
        assert(viewModel.liveDataPopularMovies.value?.throwable == throwable)
    }

    @Test
    fun `should start loading before getting top 10 popular movies`() {
        every {
            viewModel.popularMovieUseCase.getTop10(any(), any(), any())
        } just runs

        val viewModel = MovieViewModel(viewModel.popularMovieUseCase)
        viewModel.getTop10PopularMovies()

        assert(viewModel.liveDataPopularMovies.value?.dataState == DataState.LOADING)
    }

    @Test
    fun `should transform movie list to movieUI list`() {
        val movieList = arrayListOf(createMovie(), createMovie())

        assert(viewModel.transformToMovieUI(movieList)?.get(0) is MovieUI)
        assert(viewModel.transformToMovieUI(movieList)?.get(1) is MovieUI)
    }

    private fun createMovie() = Movie(0, false, "", "", "", "", "", 0.0)

}