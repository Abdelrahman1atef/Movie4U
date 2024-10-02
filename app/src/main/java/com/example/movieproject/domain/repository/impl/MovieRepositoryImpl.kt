package com.example.movieproject.domain.repository.impl

import com.example.movieproject.data.datasource.model.CastResponse
import com.example.movieproject.data.datasource.model.MovieResponse
import com.example.movieproject.data.datasource.remote.api.NetworkResource
import com.example.movieproject.data.datasource.remote.api.NetworkState
import com.example.movieproject.data.datasource.remote.interfaces.MovieDS
import com.example.movieproject.utils.getMovies
import com.example.movieproject.utils.getMoviesCast
import com.example.myroomdatabase.Database.MovieCastDao
import com.example.myroomdatabase.Database.MovieDao
import com.example.myroomdatabase.Database.MovieEntity
import kotlinx.coroutines.flow.Flow


class MovieRepositoryImpl(
    private val movieDS: MovieDS,
    private val movieDao: MovieDao,
    private val movieCastDao: MovieCastDao
) : MovieRepository {

    override suspend fun getNowShowingMovie(): Flow<NetworkResource<MovieResponse>> =
        getMovies(movieDao) { movieDS.getNowShowingMovie() }

    override suspend fun getSeeMoreNowShowingMovie(): Flow<NetworkResource<MovieResponse>> =
        getMovies(movieDao) { movieDS.getSeeMoreNowShowingMovie() }

    override suspend fun getPopularMovie(): Flow<NetworkResource<MovieResponse>> =
        getMovies(movieDao) { movieDS.getPopularMovie() }

    override suspend fun getSeeMorePopularMovie(): Flow<NetworkResource<MovieResponse>> =
        getMovies(movieDao) { movieDS.getSeeMorePopularMovie() }

    override suspend fun getTopRatedMovie(): Flow<NetworkResource<MovieResponse>> =
        getMovies(movieDao) { movieDS.getTopRatedMovie() }

    override suspend fun getSeeMoreTopRatedMovie(): Flow<NetworkResource<MovieResponse>> =
        getMovies(movieDao) { movieDS.getSeeMoreTopRatedMovie() }

    override suspend fun getMovieCast(movieId: Int): Flow<NetworkResource<CastResponse>> =
        movieDS.getMovieCast(movieId)
//        getMoviesCast(movieCastDao) { movieDS.getMovieCast(movieId) }
}
