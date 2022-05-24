package com.husqvarna.movie.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.husqvarna.movie.ui.models.MovieUI

class MovieDiffUtil(private val oldList: List<MovieUI>, private val newList: List<MovieUI>) :
    DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList.getOrNull(oldItemPosition)?.id == newList.getOrNull(newItemPosition)?.id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList.getOrNull(oldItemPosition) == newList.getOrNull(newItemPosition)
}