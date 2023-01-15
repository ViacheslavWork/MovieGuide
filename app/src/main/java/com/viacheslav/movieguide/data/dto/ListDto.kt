package com.viacheslav.movieguide.data.dto

import com.google.gson.annotations.SerializedName

data class ListDto<T>(
    @field:SerializedName("page")
    val page: Int,

    @field:SerializedName("total_pages")
    val totalPages: Int,

    @field:SerializedName("results")
    val results: List<T>,

    @field:SerializedName("total_results")
    val totalResults: Int
)

