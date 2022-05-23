package com.husqvarna.movie.ui.adapters

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.husqvarna.movie.ui.fragments.MovieFragment
import com.husqvarna.movie.ui.models.Movie

class MovieAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int) =
        MovieFragment.newInstance(Movie("The man who sold the world", position.inc(), 1.0))
}