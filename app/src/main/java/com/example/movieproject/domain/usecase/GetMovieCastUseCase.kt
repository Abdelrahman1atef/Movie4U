package com.example.movieproject.domain.usecase

import com.example.movieproject.domain.repository.impl.MovieRepository

class GetMovieCastUseCase(private val movieRepository: MovieRepository) {
    suspend fun getMovieCast(movieId: Int) = movieRepository.getMovieCast(movieId)
}