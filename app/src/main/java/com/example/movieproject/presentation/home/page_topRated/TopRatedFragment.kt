package com.example.movieproject.presentation.home.page_topRated

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieproject.R
import com.example.movieproject.base.BaseException
import com.example.movieproject.base.SeeMoreButtonSate
import com.example.movieproject.base.ViewState
import com.example.movieproject.data.datasource.model.MovieResponse
import com.example.movieproject.databinding.FragmentTopRatedBinding
import com.example.movieproject.presentation.home.adapter_topRated.TopRatedAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TopRatedFragment : Fragment() {
    private lateinit var binding: FragmentTopRatedBinding
    private val viewModel by viewModel<TopRatedViewModel>()
    private lateinit var topRatedAdapter: TopRatedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTopRatedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeViewModel()
        componantInScreen()
    }

    private fun componantInScreen() {
       binding.topRatedLayout.btnTopRatedSeeMore.setOnClickListener {
           findNavController().navigate(R.id.action_top_rated_to_fragmentSeeMore)
           SeeMoreButtonSate.TopRated=true
       }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.topRatedMoviesStatus.collect {
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

    private fun onSuccess(data: MovieResponse) {
        if (::topRatedAdapter.isInitialized) {
            topRatedAdapter.setData(data.results)
        }
    }

    private fun initView() {
            viewModel.getTopRatedMovies()
            setupRv()
        }

    private fun setupRv() {
        topRatedAdapter= TopRatedAdapter()
        binding.topRatedLayout.rvTopRated.layoutManager= LinearLayoutManager(requireContext())
        binding.topRatedLayout.rvTopRated.adapter=topRatedAdapter
        topRatedAdapter.setOnItemClickListener {position->
            findNavController().navigate(R.id.action_top_rated_to_fragmentMovieDetails,

                Bundle().apply {
                    putInt("movie_id", topRatedAdapter.result[position].id)
                    putString("title",topRatedAdapter.result[position].title)
                    putString("vote_average",topRatedAdapter.result[position].voteAverage)
                    putString("overview",topRatedAdapter.result[position].overview)
                    putString("backdrop_path",topRatedAdapter.result[position].posterPath)
                }
            )

        }
    }


}