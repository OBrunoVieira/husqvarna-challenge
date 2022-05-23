package com.husqvarna.home.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.husqvarna.home.databinding.ActivityHomeBinding
import com.husqvarna.home.ui.views.MarginScalePageTransformer
import com.husqvarna.movie.ui.adapters.MovieAdapter

class HomeActivity : AppCompatActivity() {
    private var activityHomeBinding: ActivityHomeBinding? = null
    private val binding by lazy { activityHomeBinding!! }

    private val viewpagerMargin by lazy {
        resources.getDimension(com.husqvarna.tokens.R.dimen.spacing_eight).toInt()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            ActivityHomeBinding
                .inflate(layoutInflater)
                .also { activityHomeBinding = it }
                .root)

        setupViewPager()
    }

    private fun setupViewPager() = binding.run {
        homeViewPager.adapter = MovieAdapter(this@HomeActivity)
        homeViewPager.offscreenPageLimit = 2
        homeViewPager.setPageTransformer(MarginScalePageTransformer(viewpagerMargin))
    }
}
