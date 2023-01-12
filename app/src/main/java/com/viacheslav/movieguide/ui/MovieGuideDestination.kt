package com.viacheslav.movieguide.ui

/**
 * Created by Viacheslav Avd on 11.01.2023
 */

sealed class MovieGuideDestination(val route: String) {
    object List : MovieGuideDestination("movies_list")

    object Details : MovieGuideDestination("movie_details") {
        const val ARG_MOVIE_ID = "ARG_MOVIE_ID"
    }
}