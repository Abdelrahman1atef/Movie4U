package com.example.movieproject.utils

import com.example.movieproject.data.datasource.model.CastResponse
import com.example.movieproject.data.datasource.model.MovieResponse
import com.example.movieproject.data.datasource.remote.api.NetworkResource
import com.example.movieproject.data.datasource.remote.api.NetworkState
import com.example.myroomdatabase.Database.CastEntity
import com.example.myroomdatabase.Database.MovieCastDao
import com.example.myroomdatabase.Database.MovieDao
import com.example.myroomdatabase.Database.MovieEntity
import com.example.myroomdatabase.Database.toMovieCast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

suspend fun fetchCachedMoviesCast(movieCastDao: MovieCastDao): Flow<NetworkResource<CastResponse>> =
    flow {
        val cachedMoviesCast = movieCastDao.getAllMovies().firstOrNull()
        if (!cachedMoviesCast.isNullOrEmpty()) {
            val castResponse = CastResponse(cachedMoviesCast.map { it.toMovieCast() })
            emit(NetworkResource.Success(castResponse))
        }
    }.flowOn(Dispatchers.IO)


suspend fun fetchFromApiAndSaveToDb(
    apiCall: suspend () -> Flow<NetworkResource<CastResponse>>,
    movieCastDao: MovieCastDao
): Flow<NetworkResource<CastResponse>> = flow {
    try {
        val apiResponse = apiCall()
        apiResponse.collect { networkResource ->
            when (networkResource.status) {
                NetworkState.SUCCESS -> {
                    val movieCastData = networkResource.data
                    if (movieCastData != null) {
                        val Credidts = movieCastData.cast.map { cast ->
                            CastEntity(
                                id = cast.id,
                                name = cast.name,
                                profilePath = cast.profilePath

                            )
                        }
                        movieCastDao.insertAll(Credidts)
                        emit(NetworkResource.Success(movieCastData))
                    }
                }

                NetworkState.FAILED -> {
//                    emit(NetworkResource.Failure(networkResource.error ?: BaseException.networkError()))
                }

                else -> { /* No action for loading */
                }
            }
        }
    } catch (e: Exception) {
//        emit(NetworkResource.Failure(BaseException(500, e.message ?: "Unknown error")))
    }
}.flowOn(Dispatchers.IO)

suspend fun getMoviesCast(
    movieCastDao: MovieCastDao,
    apiCall: suspend () -> Flow<NetworkResource<CastResponse>>,
): Flow<NetworkResource<CastResponse>> = flow {
    emitAll(fetchCachedMoviesCast(movieCastDao))
    emitAll(fetchFromApiAndSaveToDb(apiCall, movieCastDao))
}