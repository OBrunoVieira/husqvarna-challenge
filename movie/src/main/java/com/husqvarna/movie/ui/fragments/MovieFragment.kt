package com.husqvarna.movie.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.husqvarna.movie.databinding.FragmentMovieBinding
import com.husqvarna.movie.ui.models.Movie

class MovieFragment : Fragment() {

    private lateinit var binding: FragmentMovieBinding

    companion object {
        private const val EXTRA_MOVIE = "EXTRA_MOVIE"

        fun newInstance(movie: Movie) = MovieFragment().apply {
            arguments = Bundle().apply {
                putParcelable(EXTRA_MOVIE, movie)
            }
        }
    }

    private var movie: Movie? = null

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

        binding.vhMovieTitleTextView.text = movie?.title
        binding.vhMovieRankingTextView.text = movie?.position?.toString()
    }
}