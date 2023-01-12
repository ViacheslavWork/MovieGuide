package com.viacheslav.movieguide.data.dto

import com.google.gson.annotations.SerializedName

data class GenreDto(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int,
)