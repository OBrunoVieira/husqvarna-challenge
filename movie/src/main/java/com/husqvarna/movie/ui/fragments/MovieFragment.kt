package com.husqvarna.movie.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.husqvarna.movie.BuildConfig
import com.husqvarna.movie.databinding.FragmentMovieBinding
import com.husqvarna.movie.ui.models.MovieUI

class MovieFragment : Fragment() {

    private lateinit var binding: FragmentMovieBinding

    companion object {
        private const val EXTRA_MOVIE = "EXTRA_MOVIE"

        fun newInstance(movie: MovieUI?) = MovieFragment().apply {
            arguments = Bundle().apply {
                putParcelable(EXTRA_MOVIE, movie)
            }
        }
    }

    private var movie: MovieUI? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentMovieBinding.inflate(inflater).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            movie = it.getParcelable(EXTRA_MOVIE)
        }

        movie?.imagePath?.let {
            Glide.with(this)
                .load(BuildConfig.TMDB_IMAGE_PATH.format(it))
                .into(binding.vhMoviePosterImageView)
        }
    }
}