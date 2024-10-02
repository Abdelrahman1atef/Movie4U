package com.example.movieproject.domain.usecase

import com.example.movieproject.data.datasource.model.MovieResponse
import com.example.movieproject.data.datasource.remote.api.NetworkResource
import com.example.movieproject.data.datasource.remote.interfaces.MovieDS
import com.example.movieproject.domain.repository.impl.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetPopularMovieUseCase(private val movieRepository: MovieRepository) {
    suspend fun getPopularMovie(): Flow<NetworkResource<MovieResponse>> =
        movieRepository.getPopularMovie()

    suspend fun getSeeMorePopularMovie(): Flow<NetworkResource<MovieResponse>> =
        movieRepository.getSeeMorePopularMovie()
}