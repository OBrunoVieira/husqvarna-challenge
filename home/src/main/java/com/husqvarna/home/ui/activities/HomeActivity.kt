package com.husqvarna.home.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.husqvarna.core.viewmodel.DataSource
import com.husqvarna.core.viewmodel.DataState
import com.husqvarna.home.databinding.ActivityHomeBinding
import com.husqvarna.home.ui.transformers.MarginScalePageTransformer
import com.husqvarna.movie.ui.adapters.MovieAdapter
import com.husqvarna.movie.ui.listeners.PageClickListener
import com.husqvarna.movie.ui.models.MovieUI
import com.husqvarna.movie.viewmodel.MovieViewModel
import com.husqvarna.movie_details.ui.activities.MovieDetailActivity
import com.husqvarna.movie_details.ui.activities.MovieDetailActivity.Companion.EXTRA_MOVIE_ID
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity(), PageClickListener {
    companion object {
        private const val INSTANCE_STATE_MOVIES = "INSTANCE_STATE_MOVIES"
    }

    private var activityHomeBinding: ActivityHomeBinding? = null
    private val binding by lazy { activityHomeBinding!! }

    private val viewpagerMargin by lazy {
        resources.getDimension(com.husqvarna.tokens.R.dimen.spacing_eight).toInt()
    }

    private val movieAdapter by lazy { MovieAdapter(this) }
    private val movieViewModel: MovieViewModel by viewModel()

    private var movies: ArrayList<MovieUI>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movies = savedInstanceState?.getParcelableArrayList(INSTANCE_STATE_MOVIES)

        setContentView(
            ActivityHomeBinding
                .inflate(layoutInflater)
                .also { activityHomeBinding = it }
                .root)

        setupViewPager()

        movies?.let {
            buildSuccessfulState(it)
        } ?: run {
            movieViewModel.liveDataPopularMovies.observe(this, observePopularMovies())
            movieViewModel.getTop10PopularMovies()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(INSTANCE_STATE_MOVIES, movies)
    }

    override fun onPageClicked(movie: MovieUI?) {
        movie?.let {
            startActivity(
                Intent(this, MovieDetailActivity::class.java)
                    .putExtra(EXTRA_MOVIE_ID, movie.id)
            )
        }
    }

    private fun setupViewPager() = binding.run {
        homeViewPager.adapter = movieAdapter
        homeViewPager.offscreenPageLimit = 2
        homeViewPager.setPageTransformer(MarginScalePageTransformer(viewpagerMargin))
        homeViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                homeGroup.post {
                    val movie = movieAdapter.items.getOrNull(position)
                    binding.homePagerTitleTextView.text = movie?.title
                    binding.homePagerVotingTextView.text = movie?.rating?.toString()
                }
            }
        })
    }

    private fun observePopularMovies() = Observer<DataSource<List<MovieUI>>> {
        when (it.dataState) {
            DataState.LOADING -> {
                binding.homeGroup.isGone = true
                binding.homeErrorView.isGone = true
                binding.homeProgressBar.isVisible = true
            }

            DataState.SUCCESS -> {
                buildSuccessfulState(it.data)
            }

            DataState.ERROR -> {
                buildErrorState(it.throwable)
            }
        }
    }

    private fun buildSuccessfulState(items: List<MovieUI>?) = binding.run {
        this@HomeActivity.movies = items?.let { ArrayList(it) }
        homeProgressBar.isGone = true
        homeErrorView.isGone = true
        homeGroup.isVisible = true

        items?.let { movieAdapter.setItems(items) }
    }

    private fun buildErrorState(throwable: Throwable?) = binding.run {
        homeProgressBar.isGone = true
        homeGroup.isGone = true
        homeErrorView
            .throwable(throwable)
            .reload {
                movieViewModel.getTop10PopularMovies()
            }
            .show()
    }
}
