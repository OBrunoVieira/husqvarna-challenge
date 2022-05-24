package com.husqvarna.movie.ui.adapters

import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.husqvarna.movie.ui.fragments.MovieFragment
import com.husqvarna.movie.ui.models.MovieUI

class MovieAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    var items = arrayListOf<MovieUI>()
        private set

    fun setItems(newItems: List<MovieUI>) {
        val callback = MovieDiffUtil(items, newItems)
        val diff = DiffUtil.calculateDiff(callback)
        items.clear()
        items.addAll(newItems)
        diff.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = items.size

    override fun createFragment(position: Int) =
        MovieFragment.newInstance(items.getOrNull(position))
}