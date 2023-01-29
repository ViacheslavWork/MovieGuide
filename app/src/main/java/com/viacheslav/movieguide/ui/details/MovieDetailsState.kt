package com.viacheslav.movieguide.ui.details

/**
 * Created by Viacheslav Avd on 29.01.2023
 */
sealed class MovieDetailsState {
    data class MovieLoaded(val movieDetailsUi: DetailsUi) : MovieDetailsState()
    object FailedToLoad : MovieDetailsState()
    object Initial : MovieDetailsState()
}