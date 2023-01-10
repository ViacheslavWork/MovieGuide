package com.viacheslav.movieguide.ui.movies_list

import androidx.annotation.DrawableRes

/**
 * Created by Viacheslav Avd on 09.01.2023
 */
data class MovieItemUi(
    val id: Int,
    val title: String,
    val ageLimit: Int,
    val genres: String,
    val numberOfStars: Int,
    val numberOfReviews: Int,
    val duration: Int,
    val isLiked: Boolean = false,
    @DrawableRes val poster: Int
)
