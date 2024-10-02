package com.example.movieproject.data.datasource.remote.interfaces

import com.example.movieproject.data.datasource.model.CastResponse
import com.example.movieproject.data.datasource.model.MovieResponse
import com.example.movieproject.data.datasource.remote.api.NetworkResource
import kotlinx.coroutines.flow.Flow

interface MovieDS {
    suspend fun getNowShowingMovie(): Flow<NetworkResource<MovieResponse>>
    suspend fun getSeeMoreNowShowingMovie(): Flow<NetworkResource<MovieResponse>>

    suspend fun getPopularMovie(): Flow<NetworkResource<MovieResponse>>
    suspend fun getSeeMorePopularMovie(): Flow<NetworkResource<MovieResponse>>

    suspend fun getTopRatedMovie(): Flow<NetworkResource<MovieResponse>>
    suspend fun getSeeMoreTopRatedMovie(): Flow<NetworkResource<MovieResponse>>

    suspend fun getMovieCast(movieId: Int): Flow<NetworkResource<CastResponse>>

}