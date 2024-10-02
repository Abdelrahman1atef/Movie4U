package com.example.movieproject.presentation.home.page_home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieproject.R
import com.example.movieproject.base.BaseException
import com.example.movieproject.base.SeeMoreButtonSate
import com.example.movieproject.base.ViewState
import com.example.movieproject.data.datasource.model.MovieResponse
import com.example.movieproject.databinding.FragmentHomeBinding
import com.example.movieproject.presentation.home.adapter_home.adapter_nowShowing.NowShowingAdapter
import com.example.movieproject.presentation.home.adapter_home.adapter_popular.PopularAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModel<HomeViewModel>()
    private lateinit var nowShowingAdapter: NowShowingAdapter
    private lateinit var popularAdapter: PopularAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        observeViewModel()
    }

    private fun componantInScreen() {
        binding.nowShowingLayout.btnNowShowingSeeMore.setOnClickListener {
            findNavController().navigate(R.id.action_home_page_to_fragmentSeeMore)
            SeeMoreButtonSate.NowShowing=true
        }
        binding.popularLayout.btnPopularSeeMore.setOnClickListener {
            findNavController().navigate(R.id.action_home_page_to_fragmentSeeMore)
            SeeMoreButtonSate.Popular=true
        }

    }
    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.showingNowMoviesStatus.collect {
                when (it) {
                    is ViewState.Loaded -> onSuccess(it.data!!)
                    is ViewState.Error -> onError(it.error!!)
                    else -> {}
                }
            }
        }
        lifecycleScope.launch {
            viewModel.popularMoviesStatus.collect {
                when (it) {
                    is ViewState.Loaded -> onPopularSuccess(it.data!!)
                    is ViewState.Error -> onPopularError(it.error!!)
                    else -> {}
                }
            }
        }
    }
    private fun onPopularError(error: BaseException) {
        Toast.makeText(requireContext(), error.statusMessage, Toast.LENGTH_SHORT).show()
    }
    private fun onError(error: BaseException) {
        Toast.makeText(requireContext(), error.statusMessage, Toast.LENGTH_SHORT).show()
    }
    private fun onPopularSuccess(data: MovieResponse) {
        if (::popularAdapter.isInitialized) {
            popularAdapter.setData(data.results)
        }
    }
    private fun onSuccess(data: MovieResponse) {
        if (::nowShowingAdapter.isInitialized) {
            nowShowingAdapter.setData(data.results)
        }
    }
    private fun initView() {
        componantInScreen()
        getViewModel()
        setupRv()
    }
    private fun getViewModel() {
        viewModel.getNowShowingMovie()
        viewModel.getPopularMovie()
    }
    private fun setupRv() {
       setupNowShowingRv()
        setupPopularRv()
    }
    private fun setupPopularRv() {
        popularAdapter = PopularAdapter()
        binding.popularLayout.rvPopular.layoutManager =
            LinearLayoutManager(requireContext())
        binding.popularLayout.rvPopular.adapter = popularAdapter

        popularAdapter.setOnItemClickListener {position->
            findNavController().navigate(R.id.action_home_page_to_fragmentMovieDetails,

                Bundle().apply {
                    putInt("movie_id", popularAdapter.result[position].id)
                    putString("title",popularAdapter.result[position].title)
                    putString("vote_average",popularAdapter.result[position].voteAverage)
                    putString("overview",popularAdapter.result[position].overview)
                    putString("backdrop_path",popularAdapter.result[position].posterPath)
                }
            )

        }
    }
    private fun setupNowShowingRv() {
        nowShowingAdapter = NowShowingAdapter()
        binding.nowShowingLayout.rvNowShowing.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.nowShowingLayout.rvNowShowing.adapter = nowShowingAdapter


        nowShowingAdapter.setOnItemClickListener {position->
            findNavController().navigate(R.id.action_home_page_to_fragmentMovieDetails,

                Bundle().apply {
                    putInt("movie_id", nowShowingAdapter.result[position].id)
                    putString("title",nowShowingAdapter.result[position].title)
                    putString("vote_average",nowShowingAdapter.result[position].voteAverage)
                    putString("overview",nowShowingAdapter.result[position].overview)
                    putString("backdrop_path",nowShowingAdapter.result[position].posterPath)
            }
            )
        }

    }


}