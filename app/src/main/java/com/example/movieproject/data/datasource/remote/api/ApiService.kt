package com.example.movieproject.data.datasource.remote.api

import com.example.movieproject.data.datasource.model.CastResponse
import com.example.movieproject.data.datasource.model.MovieResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    companion object {
        fun createService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
    }

    @GET(nowShowingEndPoint)
    suspend fun fetchNowShowingMovie(@Query("api_key") apiKey: String): Response<MovieResponse>

    @GET(nowShowingEndPoint)
    suspend fun fetchSeeMoreNowShowingMovie(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): Response<MovieResponse>

    @GET(popularEndPoint)
    suspend fun fetchPopularMovie(@Query("api_key") apiKey: String): Response<MovieResponse>

    @GET(popularEndPoint)

    suspend fun fetchSeeMorePopularMovie(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): Response<MovieResponse>

    @GET(topRatedEndPoint)
    suspend fun fetchTopRatedMovie(@Query("api_key") apiKey: String): Response<MovieResponse>

    @GET(topRatedEndPoint)

    suspend fun fetchSeeMoreTopRatedMovie(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): Response<MovieResponse>

    @GET("movie/{movie_id}/credits")
    suspend fun fetchMovieCast(@Path("movie_id") movieId: Int, @Query("api_key") apiKey: String): Response<CastResponse>

}