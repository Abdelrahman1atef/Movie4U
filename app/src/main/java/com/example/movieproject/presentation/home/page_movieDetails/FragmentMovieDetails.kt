package com.example.movieproject.presentation.home.page_movieDetails

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.movieproject.base.BaseException
import com.example.movieproject.base.ViewState
import com.example.movieproject.data.datasource.model.CastDetails
import com.example.movieproject.data.datasource.model.CastResponse
import com.example.movieproject.data.datasource.model.MovieDetails
import com.example.movieproject.data.datasource.model.MovieResponse
import com.example.movieproject.data.datasource.remote.api.imageUrl
import com.example.movieproject.databinding.FragmentMovieDetailsBinding
import com.example.movieproject.presentation.home.adapter_movieDetails.MovieDetailsAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class FragmentMovieDetails : Fragment() {
    private val viewModel by viewModel<MovieDetailsViewModel>()
    private lateinit var movieDetailsAdapter: MovieDetailsAdapter
    private lateinit var binding: FragmentMovieDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieId = arguments?.getInt("movie_id") ?: 0
        if (movieId != 0) {
            initView(movieId) // Pass movieId to initView()
            observeViewModel()
        } else {
            // Handle the case where movieId is not available
            Toast.makeText(requireContext(), "Movie ID not found", Toast.LENGTH_SHORT).show()
        }

        initView(movieId)
        observeViewModel()
    }

    private fun initView(movieId:Int) {
        getViewModel(movieId)
        setupRv()
    }
    private fun setupRv() {
        movieDetailsAdapter = MovieDetailsAdapter()
        binding.castLayout.rvCast.adapter = movieDetailsAdapter
    }
    private fun getViewModel(movieId: Int) {
        viewModel.getMovieCast(movieId)
    }
    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.movieCastStatus.collect {
                when (it) {
                    is ViewState.Loaded -> onSuccess(it.data!!)
                    is ViewState.Error -> onError(it.error!!)
                    else -> {}
                }
            }
        }
    }

    private fun onError(error: BaseException) {
        Toast.makeText(requireContext(), error.statusMessage, Toast.LENGTH_SHORT).show()
    }

    private fun onSuccess(data: CastResponse) {
        if (::movieDetailsAdapter.isInitialized) {
            movieDetailsAdapter.setData(data.cast)
            binding.tvMovieTitle.text=arguments?.getString("title")?:""
            binding.tvRatingTitle.text=arguments?.getString("vote_average")?:""
            binding.tvDescription.text=arguments?.getString("overview")?:""
            val posterPath=arguments?.getString("backdrop_path")?:""
            Log.e("Tag","$imageUrl$posterPath")
            Glide.with(requireContext()).asBitmap().load("$imageUrl$posterPath").into(binding.ivMoviePic)
        }
    }


}