package com.example.movieproject.presentation.home.page_movieDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieproject.base.SeeMoreButtonSate
import com.example.movieproject.base.ViewState
import com.example.movieproject.data.datasource.model.CastResponse
import com.example.movieproject.data.datasource.model.MovieResponse
import com.example.movieproject.data.datasource.remote.api.NetworkState
import com.example.movieproject.domain.usecase.GetMovieCastUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val getMovieCastUseCase: GetMovieCastUseCase): ViewModel() {

    private val _movieCastStatus =
        MutableStateFlow<ViewState<CastResponse>>(ViewState.Empty())
    val movieCastStatus: StateFlow<ViewState<CastResponse>> = _movieCastStatus

    fun getMovieCast(movieId: Int){
        viewModelScope.launch {
            getMovieCastUseCase.getMovieCast(movieId).collect {
                val state: ViewState<CastResponse> = when (it.status) {
                    NetworkState.SUCCESS -> ViewState.Loaded(it.data!!)
                    NetworkState.FAILED -> ViewState.Error(it.error)
                    else -> ViewState.Empty()
                }
                _movieCastStatus.value = state
            }
        }
    }
}