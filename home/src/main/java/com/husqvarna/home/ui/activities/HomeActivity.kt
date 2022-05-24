package com.husqvarna.home.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.husqvarna.core.viewmodel.DataSource
import com.husqvarna.core.viewmodel.DataState
import com.husqvarna.home.databinding.ActivityHomeBinding
import com.husqvarna.home.ui.views.MarginScalePageTransformer
import com.husqvarna.movie.ui.adapters.MovieAdapter
import com.husqvarna.movie.ui.models.MovieUI
import com.husqvarna.movie.viewmodel.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {
    private var activityHomeBinding: ActivityHomeBinding? = null
    private val binding by lazy { activityHomeBinding!! }

    private val viewpagerMargin by lazy {
        resources.getDimension(com.husqvarna.tokens.R.dimen.spacing_eight).toInt()
    }

    private val movieAdapter by lazy { MovieAdapter(this) }
    private val movieViewModel: MovieViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            ActivityHomeBinding
                .inflate(layoutInflater)
                .also { activityHomeBinding = it }
                .root)

        setupViewPager()

        movieViewModel.liveDataPopularMovies.observe(this, observePopularMovies())
        movieViewModel.getTop10PopularMovies()
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
                binding.homeProgressBar.isVisible = true
                binding.homeGroup.isGone = true
            }

            DataState.SUCCESS -> {
                binding.homeProgressBar.isGone = true
                binding.homeGroup.isVisible = true

                it.data?.let { data ->
                    movieAdapter.setItems(data)
                }
            }

            DataState.ERROR -> {

            }
        }
    }
}
