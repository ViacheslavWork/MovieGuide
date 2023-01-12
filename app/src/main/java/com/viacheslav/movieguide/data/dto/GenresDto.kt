package com.viacheslav.movieguide.data.dto

import com.google.gson.annotations.SerializedName

data class GenresDto(
    @field:SerializedName("genres")
    val genres: List<GenreDto>
)

