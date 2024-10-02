package com.example.movieproject.domain.usecase

import android.util.Log
import com.example.movieproject.data.datasource.model.MovieResponse
import com.example.movieproject.data.datasource.remote.api.NetworkResource
import com.example.movieproject.domain.repository.impl.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetNowShowingMovieUseCase(private val movieRepository: MovieRepository) {
    suspend fun getNowShowingMovie(): Flow<NetworkResource< MovieResponse>> =
        movieRepository.getNowShowingMovie()

    suspend fun getSeeMoreNowShowingMovie():Flow<NetworkResource<MovieResponse>> =
        movieRepository.getSeeMoreNowShowingMovie()
}