package com.example.movieproject.data.datasource.model

import com.google.gson.annotations.SerializedName

data class MovieResponse(val results: List<MovieDetails>)
data class CastResponse(val cast: List<CastDetails>)

data class MovieDetails(
    @SerializedName("id") val id:Int,
    @SerializedName("title") val title:String,
    @SerializedName("overview") val overview:String,
    @SerializedName("release_date") val releaseDate:String,
    @SerializedName("vote_average") val voteAverage:String,
    @SerializedName("poster_path") val posterPath:String,
    @SerializedName("backdrop_path") val backdropPath:String
)
data class CastDetails(
    @SerializedName("id") val id:Int,
    @SerializedName("name") val name:String,
    @SerializedName("profile_path") val profilePath:String
)
