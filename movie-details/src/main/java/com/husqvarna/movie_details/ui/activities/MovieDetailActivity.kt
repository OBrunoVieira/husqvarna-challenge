package com.husqvarna.movie_details.ui.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.husqvarna.core.BuildConfig
import com.husqvarna.core.viewmodel.DataSource
import com.husqvarna.core.viewmodel.DataState
import com.husqvarna.movie_details.R
import com.husqvarna.movie_details.databinding.ActivityMovieDetailBinding
import com.husqvarna.movie_details.ui.models.MovieDetailUI
import com.husqvarna.movie_details.viewmodel.MovieDetailsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"
        const val INSTANCE_STATE_MOVIE = "INSTANCE_STATE_MOVIE"
    }

    private var activityMovieDetailBinding: ActivityMovieDetailBinding? = null
    private val binding by lazy { activityMovieDetailBinding!! }

    private val movieDetailViewModel: MovieDetailsViewModel by viewModel()

    private var movieId: Long = 0L
    private var movieDetail: MovieDetailUI? = null

    private val cornerRadius by lazy {
        resources.getDimension(com.husqvarna.tokens.R.dimen.spacing_eight).toInt()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieId = intent.getLongExtra(EXTRA_MOVIE_ID, 0L)
        movieDetail = savedInstanceState?.getParcelable(INSTANCE_STATE_MOVIE)

        setContentView(
            ActivityMovieDetailBinding
                .inflate(layoutInflater)
                .also { activityMovieDetailBinding = it }
                .root
        )

        configureToolbar()

        movieDetail?.let {
            buildSuccessfulState(it)
        } ?: run {
            movieDetailViewModel.liveDataMovieDetail.observe(this, observeDetails())
            movieDetailViewModel.getMovieDetail(movieId)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(INSTANCE_STATE_MOVIE, movieDetail)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }


    private fun configureToolbar() {
        setSupportActionBar(binding.movieDetailToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun observeDetails() = Observer<DataSource<MovieDetailUI>> {
        when (it.dataState) {
            DataState.LOADING -> {
                binding.movieDetailInfoGroup.isGone = true
                binding.movieDetailErrorView.isGone = true
                binding.movieDetailProgressBar.isVisible = true
            }

            DataState.SUCCESS -> {
                buildSuccessfulState(it.data)
            }

            DataState.ERROR -> {
                buildErrorState(it.throwable)
            }
        }
    }

    private fun buildSuccessfulState(detail: MovieDetailUI?) = binding.run {
        this@MovieDetailActivity.movieDetail = detail
        movieDetailProgressBar.isGone = true
        movieDetailErrorView.isGone = true
        movieDetailInfoGroup.isVisible = true

        detail?.let {
            Glide.with(this@MovieDetailActivity)
                .load(BuildConfig.TMDB_IMAGE_PATH.format(it.posterPath))
                .transform(CenterCrop(), RoundedCorners(cornerRadius))
                .into(movieDetailPoster)

            movieDetailTitleTextView.text = it.title
            movieDetailSynopsisDescriptionTextView.text = it.description
            movieDetailItemGenre.setValue(it.genre)
            movieDetailItemRating.setValue(it.rating.toString())
            movieDetailItemDuration.setValue(
                getString(
                    R.string.movie_detail_runtime,
                    it.runtime
                )
            )
        }

    }

    private fun buildErrorState(throwable: Throwable?) = binding.run {
        movieDetailProgressBar.isGone = true
        movieDetailInfoGroup.isGone = true

        movieDetailErrorView
            .throwable(throwable)
            .reload { movieDetailViewModel.getMovieDetail(movieId) }
            .show()
    }
}