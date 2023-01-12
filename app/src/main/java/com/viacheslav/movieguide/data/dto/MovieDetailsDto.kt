package com.viacheslav.movieguide.data.dto

import com.google.gson.annotations.SerializedName

data class MovieDetailsDto(

	@field:SerializedName("original_language")
    val originalLanguage: String? = null,

	@field:SerializedName("imdb_id")
    val imdbId: String? = null,

	@field:SerializedName("video")
    val video: Boolean? = null,

	@field:SerializedName("title")
    val title: String,

	@field:SerializedName("backdrop_path")
    val backdropPath: String? = null,

	@field:SerializedName("revenue")
    val revenue: Int? = null,

	@field:SerializedName("genres")
    val genres: List<GenreDto>,

	@field:SerializedName("popularity")
    val popularity: Any? = null,

	@field:SerializedName("id")
    val id: Int,

	@field:SerializedName("vote_count")
    val voteCount: Int,

	@field:SerializedName("budget")
    val budget: Int? = null,

	@field:SerializedName("overview")
    val overview: String? = null,

	@field:SerializedName("original_title")
    val originalTitle: String? = null,

	@field:SerializedName("runtime")
    val runtime: Int? = null,

	@field:SerializedName("poster_path")
    val posterPath: String? = null,

	@field:SerializedName("release_date")
    val releaseDate: String? = null,

	@field:SerializedName("vote_average")
    val voteAverage: Float,

	@field:SerializedName("belongs_to_collection")
    val belongsToCollection: Any? = null,

	@field:SerializedName("tagline")
    val tagline: String? = null,

	@field:SerializedName("adult")
    val adult: Boolean,

	@field:SerializedName("homepage")
    val homepage: String? = null,

	@field:SerializedName("status")
    val status: String? = null
)
