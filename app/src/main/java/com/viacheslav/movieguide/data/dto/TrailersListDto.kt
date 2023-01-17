package com.viacheslav.movieguide.data.dto

import com.google.gson.annotations.SerializedName

data class TrailersListDto(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("results")
	val results: List<TrailerDto>
)

data class TrailerDto(

	@field:SerializedName("site")
	val site: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("official")
	val official: Boolean,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("published_at")
	val publishedAt: String,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("key")
	val key: String
)
