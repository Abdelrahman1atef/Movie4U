package com.example.movieproject.presentation.home.page_topRated

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieproject.base.ViewState
import com.example.movieproject.data.datasource.model.MovieResponse
import com.example.movieproject.data.datasource.remote.api.NetworkState
import com.example.movieproject.domain.usecase.GetTopRatedMovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TopRatedViewModel(
    private val getTopRatedMovieUseCase: GetTopRatedMovieUseCase,
) : ViewModel() {

    private val _topRatedMoviesStatus =
        MutableStateFlow<ViewState<MovieResponse>>(ViewState.Empty())
    val topRatedMoviesStatus: StateFlow<ViewState<MovieResponse>> = _topRatedMoviesStatus

    fun getTopRatedMovies() {
        viewModelScope.launch {
            getTopRatedMovieUseCase.getTopRatedMovie().collect {
                val state: ViewState<MovieResponse> = when (it.status) {
                    NetworkState.SUCCESS -> ViewState.Loaded(it.data!!)
                    NetworkState.FAILED -> ViewState.Error(it.error)
                    else -> ViewState.Empty()
                }
                _topRatedMoviesStatus.value = state
            }
        }
    }
}