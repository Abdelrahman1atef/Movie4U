package com.example.movieproject.presentation.home.page_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieproject.base.ViewState
import com.example.movieproject.data.datasource.model.MovieResponse
import com.example.movieproject.data.datasource.remote.api.NetworkState
import com.example.movieproject.domain.usecase.GetNowShowingMovieUseCase
import com.example.movieproject.domain.usecase.GetPopularMovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getNowShowingMovieUseCase: GetNowShowingMovieUseCase,
    private val getPopularMovieUseCase: GetPopularMovieUseCase
): ViewModel() {

    private val _showingNowMoviesStatus=
        MutableStateFlow<ViewState<MovieResponse>>(ViewState.Empty())
    val showingNowMoviesStatus: StateFlow<ViewState<MovieResponse>> = _showingNowMoviesStatus

        fun getNowShowingMovie() {
            viewModelScope.launch {
                getNowShowingMovieUseCase.getNowShowingMovie().collect{
                    val state : ViewState<MovieResponse> =when (it.status) {
                        NetworkState.SUCCESS -> ViewState.Loaded(it.data!!)
                        NetworkState.FAILED -> ViewState.Error(it.error)
                        else -> ViewState.Empty()
                    }
                    _showingNowMoviesStatus.value=state
                }
            }
        }

    private val _popularMoviesStatus=
        MutableStateFlow<ViewState<MovieResponse>>(ViewState.Empty())

    val popularMoviesStatus: StateFlow<ViewState<MovieResponse>> = _popularMoviesStatus

    fun getPopularMovie() {
        viewModelScope.launch {
            getPopularMovieUseCase.getPopularMovie().collect{
                val state : ViewState<MovieResponse> =when (it.status) {
                    NetworkState.SUCCESS -> ViewState.Loaded(it.data!!)
                    NetworkState.FAILED -> ViewState.Error(it.error)
                    else -> ViewState.Empty()
                }
                _popularMoviesStatus.value=state
            }
        }
    }
}