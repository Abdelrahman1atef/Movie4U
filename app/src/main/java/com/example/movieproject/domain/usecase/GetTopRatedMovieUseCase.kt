package com.example.movieproject.domain.usecase

import com.example.movieproject.data.datasource.model.MovieResponse
import com.example.movieproject.data.datasource.remote.api.NetworkResource
import com.example.movieproject.domain.repository.impl.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetTopRatedMovieUseCase(private val movieRepository: MovieRepository)  {
    suspend fun getTopRatedMovie(): Flow<NetworkResource<MovieResponse>> =
        movieRepository.getTopRatedMovie()

    suspend fun getSeeMoreTopRatedMovie(): Flow<NetworkResource<MovieResponse>> =
        movieRepository.getSeeMoreTopRatedMovie()
}