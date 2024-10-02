package com.example.movieproject.presentation.home.page_seeMore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieproject.R
import com.example.movieproject.base.BaseException
import com.example.movieproject.base.SeeMoreButtonSate
import com.example.movieproject.base.ViewState
import com.example.movieproject.data.datasource.model.MovieResponse
import com.example.movieproject.databinding.FragmentSeeMoreBinding
import com.example.movieproject.presentation.home.adapter_seeMore.SeeMoreAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class FragmentSeeMore : Fragment() {
    private lateinit var binding: FragmentSeeMoreBinding
    private val viewModel by viewModel<SeeMoreViewModel>()
    private lateinit var seeMoreAdapter: SeeMoreAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSeeMoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initView()
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.SeeMoreMoviesStatus.collect {
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
        if (::seeMoreAdapter.isInitialized) {
            seeMoreAdapter.setData(data.results)
        }
    }

    private fun initView() {
        if(SeeMoreButtonSate.NowShowing)viewModel.getSeeMoreNowShowingMovies()
        if(SeeMoreButtonSate.Popular)viewModel.getSeeMorePopularMovieUseCase()
        if(SeeMoreButtonSate.TopRated)viewModel.getSeeMoreTopRatedMovieUseCase()

        setupRv()
    }

    private fun setupRv() {
        seeMoreAdapter = SeeMoreAdapter()
        binding.seeMoreLayout.rvSeeMore.layoutManager = GridLayoutManager(requireContext(),2)
        binding.seeMoreLayout.rvSeeMore.adapter = seeMoreAdapter
        seeMoreAdapter.setOnItemClickListener {position->
            findNavController().navigate(
                R.id.action_fragmentSeeMore_to_fragmentMovieDetails,

                Bundle().apply {
                    putInt("movie_id", seeMoreAdapter.result[position].id)
                    putString("title",seeMoreAdapter.result[position].title)
                    putString("vote_average",seeMoreAdapter.result[position].voteAverage)
                    putString("overview",seeMoreAdapter.result[position].overview)
                    putString("backdrop_path",seeMoreAdapter.result[position].posterPath)
                }
            )

        }
    }

}