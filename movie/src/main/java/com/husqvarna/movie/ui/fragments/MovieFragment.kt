package com.husqvarna.movie.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.husqvarna.core.BuildConfig
import com.husqvarna.movie.databinding.FragmentMovieBinding
import com.husqvarna.movie.ui.listeners.PageClickListener
import com.husqvarna.movie.ui.models.MovieUI

class MovieFragment : Fragment() {
    companion object {
        private const val EXTRA_MOVIE = "EXTRA_MOVIE"
        private const val INSTANCE_STATE_MOVIE = "INSTANCE_STATE_MOVIE"

        fun newInstance(movie: MovieUI?) = MovieFragment().apply {
            arguments = Bundle().apply {
                putParcelable(EXTRA_MOVIE, movie)
            }
        }
    }

    private lateinit var binding: FragmentMovieBinding

    private var movie: MovieUI? = null
    private var listener: PageClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentMovieBinding.inflate(inflater).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movie = savedInstanceState?.getParcelable(INSTANCE_STATE_MOVIE)
            ?: arguments?.getParcelable(EXTRA_MOVIE)

        binding.vhMovieCardView.setOnClickListener {
            listener?.onPageClicked(movie)
        }

        movie?.imagePath?.let {
            Glide.with(this)
                .load(BuildConfig.TMDB_IMAGE_PATH.format(it))
                .into(binding.vhMoviePosterImageView)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(INSTANCE_STATE_MOVIE, movie)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is PageClickListener) {
            listener = context
        }
    }
}