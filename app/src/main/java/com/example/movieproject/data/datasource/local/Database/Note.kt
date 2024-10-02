package com.example.myroomdatabase.Database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movieproject.data.datasource.model.CastDetails
import com.example.movieproject.data.datasource.model.CastResponse
import com.example.movieproject.data.datasource.model.MovieDetails
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val voteAverage: String,
    val posterPath: String,
    val backdropPath: String
)
@Entity(tableName = "cast")
data class CastEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val profilePath: String
)

fun MovieEntity.toMovieDetails(): MovieDetails = MovieDetails(
    id = id,
    title = title,
    overview = overview,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    posterPath = posterPath,
    backdropPath = backdropPath
)

fun CastEntity.toMovieCast(): CastDetails = CastDetails(
    id = id,
    name = name,
    profilePath = profilePath
)

