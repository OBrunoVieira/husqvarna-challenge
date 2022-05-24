package com.husqvarna.movie_details.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.husqvarna.core.viewmodel.DataState
import com.husqvarna.movie_details.repository.models.MovieDetail
import com.husqvarna.movie_details.viewmodel.MovieDetailsViewModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

class MovieDetailsViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val viewModel = MovieDetailsViewModel(mockk(relaxed = true))

    @Test
    fun `should get movie detail successfully`() {
        val movieDetail = MovieDetail(0, "", "", "", 0.0, 0, null)

        every {
            viewModel.movieDetailUseCase.getDetail(any(), any(), any(), any())
        } answers {
            thirdArg<(MovieDetail?) -> Unit>().invoke(movieDetail)
        }

        viewModel.getMovieDetail(123)

        assert(viewModel.liveDataMovieDetail.value?.dataState == DataState.SUCCESS)
        assert(
            viewModel.liveDataMovieDetail.value?.data ==
                    viewModel.transformToMovieDetailUI(movieDetail)
        )
    }

    @Test
    fun `should get movie detail with failure`() {
        val throwable = Throwable()

        every {
            viewModel.movieDetailUseCase.getDetail(any(), any(), any(), any())
        } answers {
            lastArg<(Throwable?) -> Unit>().invoke(throwable)
        }

        viewModel.getMovieDetail(123)

        assert(viewModel.liveDataMovieDetail.value?.dataState == DataState.ERROR)
        assert(viewModel.liveDataMovieDetail.value?.throwable == throwable)
    }
}