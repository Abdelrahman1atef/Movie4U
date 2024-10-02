package com.example.movieproject.utils


import com.example.movieproject.base.BaseException
import com.example.movieproject.data.datasource.model.CastResponse
import com.example.movieproject.data.datasource.model.MovieResponse
import com.example.movieproject.data.datasource.remote.api.NetworkResource
import com.example.movieproject.data.datasource.remote.api.NetworkState
import com.example.movieproject.domain.repository.impl.MovieRepository
import com.example.myroomdatabase.Database.MovieCastDao
import com.example.myroomdatabase.Database.MovieDao
import com.example.myroomdatabase.Database.MovieEntity
import com.example.myroomdatabase.Database.toMovieCast
import com.example.myroomdatabase.Database.toMovieDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

// Function to fetch cached movies
suspend fun fetchCachedMovies(movieDao: MovieDao): Flow<NetworkResource<MovieResponse>> = flow {
    val cachedMovies = movieDao.getAllMovies().firstOrNull()
        val movieResponse = MovieResponse(cachedMovies!!.map { it.toMovieDetails() })
        emit(NetworkResource.Success(movieResponse))


}.flowOn(Dispatchers.IO)



// Function to handle API response and save to DB
suspend fun fetchFromApiAndSaveToDb(
    apiCall: suspend () -> Flow<NetworkResource<MovieResponse>>,
    movieDao: MovieDao
): Flow<NetworkResource<MovieResponse>> = flow {
    try {
        val apiResponse = apiCall()
        apiResponse.collect { networkResource ->
            when (networkResource.status) {
                NetworkState.SUCCESS -> {
                    val movieData = networkResource.data
                    if (movieData != null) {
                        val movies = movieData.results.map { movie ->
                            MovieEntity(
                                id = movie.id,
                                title = movie.title,
                                overview = movie.overview,
                                releaseDate = movie.releaseDate ?: "",
                                voteAverage = movie.voteAverage.toString(),
                                posterPath = movie.posterPath,
                                backdropPath = movie.backdropPath ?: ""
                            )
                        }
                        movieDao.insertAll(movies)
                        emit(NetworkResource.Success(movieData))
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

// Function to handle the entire movie fetching process
suspend fun getMovies(
    movieDao: MovieDao,
    apiCall: suspend () -> Flow<NetworkResource<MovieResponse>>,
): Flow<NetworkResource<MovieResponse>> = flow {
    emitAll(fetchCachedMovies(movieDao))
    emitAll(fetchFromApiAndSaveToDb(apiCall, movieDao))
}

