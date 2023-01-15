package com.viacheslav.movieguide.data.dto

import com.google.gson.annotations.SerializedName

data class CreditsDto(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("cast")
    val cast: List<CastItemDto>,

    @field:SerializedName("crew")
    val crew: List<CrewItem>
)

