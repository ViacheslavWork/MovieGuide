package com.viacheslav.movieguide.ui.movies_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viacheslav.movieguide.data.MoviesRepositoryImpl
import com.viacheslav.movieguide.data.retrofit.moviesGuideApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Created by Viacheslav Avd on 11.01.2023
 */
fun getTestMovies(): List<MovieItemUi> {
    val movies = mutableListOf<MovieItemUi>()
    repeat(6) {
        movies.add(
            MovieItemUi(
                id = it,
                title = "Avengers: End Game",
                ageLimit = 13,
                genres = "Action, Adventure, Drama, Action, Adventure, Drama",
                numberOfStars = 4,
                numberOfReviews = 125,
                isLiked = it % 2 == 0,
                "poster = R.drawable.im_avengers_end_game"
            )
        )
    }
    return movies
}

class MoviesListViewModel : ViewModel() {

    private val repository = MoviesRepositoryImpl(moviesGuideApiService)

    private val _movies = MutableStateFlow<List<MovieItemUi>>(emptyList())
    val movies: StateFlow<List<MovieItemUi>> = _movies.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val allGenres = repository.getGenres()
            _movies.update {
                repository.getPopularMovies()
                    .map { moviesDto -> MovieItemUi.fromMovieDto(moviesDto, allGenres) }
            }
        }
    }
}