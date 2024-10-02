package com.example.movieproject.data.datasource.remote.remote_repository

import com.example.movieproject.data.datasource.model.CastResponse
import com.example.movieproject.data.datasource.model.MovieResponse
import com.example.movieproject.data.datasource.remote.api.ApiProvider
import com.example.movieproject.data.datasource.remote.api.ApiService
import com.example.movieproject.data.datasource.remote.api.NetworkResource
import com.example.movieproject.data.datasource.remote.api.apiKey
import com.example.movieproject.data.datasource.remote.interfaces.MovieDS
import kotlinx.coroutines.flow.Flow

class MovieDSImpl(private val apiService: ApiService) : MovieDS, ApiProvider() {
    override suspend fun getNowShowingMovie(): Flow<NetworkResource<MovieResponse>> =
        apiRequest { apiService.fetchNowShowingMovie(apiKey) }

    override suspend fun getSeeMoreNowShowingMovie(): Flow<NetworkResource<MovieResponse>> =
        apiRequest { apiService.fetchSeeMoreNowShowingMovie(2, apiKey) }

    override suspend fun getPopularMovie(): Flow<NetworkResource<MovieResponse>> =
        apiRequest { apiService.fetchPopularMovie(apiKey) }

    override suspend fun getSeeMorePopularMovie(): Flow<NetworkResource<MovieResponse>> =
        apiRequest { apiService.fetchSeeMorePopularMovie(2, apiKey) }

    override suspend fun getTopRatedMovie(): Flow<NetworkResource<MovieResponse>> =
        apiRequest { apiService.fetchTopRatedMovie(apiKey) }

    override suspend fun getSeeMoreTopRatedMovie(): Flow<NetworkResource<MovieResponse>> =
        apiRequest { apiService.fetchSeeMoreTopRatedMovie(2, apiKey) }

    override suspend fun getMovieCast(movieId: Int): Flow<NetworkResource<CastResponse>> =
        apiRequest { apiService.fetchMovieCast(movieId, apiKey) }
}
