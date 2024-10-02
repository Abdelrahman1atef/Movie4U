package com.example.movieproject.presentation.home.page_seeMore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieproject.base.SeeMoreButtonSate
import com.example.movieproject.base.ViewState
import com.example.movieproject.data.datasource.model.MovieResponse
import com.example.movieproject.data.datasource.remote.api.NetworkState
import com.example.movieproject.domain.usecase.GetNowShowingMovieUseCase
import com.example.movieproject.domain.usecase.GetPopularMovieUseCase
import com.example.movieproject.domain.usecase.GetTopRatedMovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SeeMoreViewModel (
    private val getSeeMoreNowShowingMovieUseCase: GetNowShowingMovieUseCase,
    private val getSeeMorePopularMovieUseCase: GetPopularMovieUseCase,
    private val getSeeMoreTopRatedMovieUseCase: GetTopRatedMovieUseCase
) : ViewModel() {

    private val _SeeMoreMoviesStatus =
        MutableStateFlow<ViewState<MovieResponse>>(ViewState.Empty())
    val SeeMoreMoviesStatus: StateFlow<ViewState<MovieResponse>> = _SeeMoreMoviesStatus

    fun getSeeMoreNowShowingMovies() {
        viewModelScope.launch {
            getSeeMoreNowShowingMovieUseCase.getSeeMoreNowShowingMovie().collect {
                val state: ViewState<MovieResponse> = when (it.status) {
                    NetworkState.SUCCESS -> ViewState.Loaded(it.data!!)
                    NetworkState.FAILED -> ViewState.Error(it.error)
                    else -> ViewState.Empty()
                }
                SeeMoreButtonSate.NowShowing=false
                _SeeMoreMoviesStatus.value = state
            }
        }
    }
    fun getSeeMorePopularMovieUseCase(){
        viewModelScope.launch {
            getSeeMorePopularMovieUseCase.getSeeMorePopularMovie().collect {
                val state: ViewState<MovieResponse> = when (it.status) {
                    NetworkState.SUCCESS -> ViewState.Loaded(it.data!!)
                    NetworkState.FAILED -> ViewState.Error(it.error)
                    else -> ViewState.Empty()
                }
                SeeMoreButtonSate.Popular=false
                _SeeMoreMoviesStatus.value = state
            }
        }
    }
    fun getSeeMoreTopRatedMovieUseCase(){
        viewModelScope.launch {
            getSeeMoreTopRatedMovieUseCase.getTopRatedMovie().collect {
                val state: ViewState<MovieResponse> = when (it.status) {
                    NetworkState.SUCCESS -> ViewState.Loaded(it.data!!)
                    NetworkState.FAILED -> ViewState.Error(it.error)
                    else -> ViewState.Empty()
                }
                SeeMoreButtonSate.TopRated=false
                _SeeMoreMoviesStatus.value = state
            }
        }
    }

}