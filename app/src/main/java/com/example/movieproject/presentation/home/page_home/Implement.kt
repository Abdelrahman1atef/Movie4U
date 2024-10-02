package com.example.movieproject.presentation.home.page_home

import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieproject.base.BaseException
import com.example.movieproject.base.ViewState
import com.example.movieproject.data.datasource.model.MovieResponse
import com.example.movieproject.databinding.FragmentHomeBinding
import com.example.movieproject.presentation.home.adapter_home.adapter_nowShowing.NowShowingAdapter
import com.example.movieproject.presentation.home.adapter_home.adapter_popular.PopularAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

object Implement : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModel<HomeViewModel>()
    private lateinit var nowShowingAdapter: NowShowingAdapter
    private lateinit var popularAdapter: PopularAdapter

    fun start(){
        initView()
        observeViewModel()
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
    }
    private fun setupNowShowingRv() {
        nowShowingAdapter = NowShowingAdapter()
        binding.nowShowingLayout.rvNowShowing.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.nowShowingLayout.rvNowShowing.adapter = nowShowingAdapter
    }

}