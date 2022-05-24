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
    private var activityMovieDetailBinding: ActivityMovieDetailBinding? = null
    private val binding by lazy { activityMovieDetailBinding!! }

    private val movieDetailViewModel: MovieDetailsViewModel by viewModel()

    companion object {
        const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"
    }

    private var movieId: Long = 0L

    private val cornerRadius by lazy {
        resources.getDimension(com.husqvarna.tokens.R.dimen.spacing_eight).toInt()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            ActivityMovieDetailBinding
                .inflate(layoutInflater)
                .also { activityMovieDetailBinding = it }
                .root
        )

        movieId = intent.getLongExtra(EXTRA_MOVIE_ID, 0L)

        setSupportActionBar(binding.movieDetailToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        movieDetailViewModel.liveDataMovieDetail.observe(this, observeDetails())
        movieDetailViewModel.getMovieDetail(movieId)
    }

    private fun observeDetails() = Observer<DataSource<MovieDetailUI>> {
        when (it.dataState) {
            DataState.LOADING -> {
                binding.movieDetailInfoGroup.isGone = true
                binding.movieDetailErrorView.isGone = true
                binding.movieDetailProgressBar.isVisible = true
            }

            DataState.SUCCESS -> {
                binding.movieDetailProgressBar.isGone = true
                binding.movieDetailErrorView.isGone = true
                binding.movieDetailInfoGroup.isVisible = true

                it.data?.let { data ->
                    Glide.with(this)
                        .load(BuildConfig.TMDB_IMAGE_PATH.format(data.posterPath))
                        .transform(CenterCrop(), RoundedCorners(cornerRadius))
                        .into(binding.movieDetailPoster)

                    binding.movieDetailTitleTextView.text = data.title
                    binding.movieDetailSynopsisDescriptionTextView.text = data.description
                    binding.movieDetailItemGenre.setValue(data.genre)
                    binding.movieDetailItemRating.setValue(data.rating.toString())
                    binding.movieDetailItemDuration.setValue(
                        getString(
                            R.string.movie_detail_runtime,
                            data.runtime
                        )
                    )
                }
            }

            DataState.ERROR -> {
                binding.movieDetailProgressBar.isGone = true
                binding.movieDetailInfoGroup.isGone = true

                binding.movieDetailErrorView
                    .throwable(it.throwable)
                    .reload {
                        movieDetailViewModel.getMovieDetail(movieId)
                    }
                    .show()
            }
        }
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

}