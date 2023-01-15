package com.viacheslav.movieguide.ui.movies_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viacheslav.movieguide.data.Result.Success
import com.viacheslav.movieguide.domain.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

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

private const val TAG = "MoviesListViewModel"

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    private val _movies = MutableStateFlow<List<MovieItemUi>>(emptyList())
    val movies: StateFlow<List<MovieItemUi>> = _movies.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val allGenresDeferred = async { repository.getGenres() }
            val popularMoviesDeferred = async { repository.getPopularMovies() }
            val allGenres = allGenresDeferred.await()
            val popularMovies = popularMoviesDeferred.await()
            if (allGenres is Success && popularMovies is Success) {
                _movies.update {
                    popularMovies.data.results
                        .map { moviesDto -> MovieItemUi.fromMovieDto(moviesDto, allGenres.data) }
                }
            }
        }
    }
}