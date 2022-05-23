package com.husqvarna.movie.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.husqvarna.movie.databinding.FragmentMovieBinding

class MovieFragment : Fragment() {

    companion object {
        fun newInstance() = MovieFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentMovieBinding.inflate(inflater).root
}