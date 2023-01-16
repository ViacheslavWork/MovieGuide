package com.viacheslav.movieguide.ui.movies_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viacheslav.movieguide.core.CoDispatchers
import com.viacheslav.movieguide.data.Result.Success
import com.viacheslav.movieguide.data.dto.GenreDto
import com.viacheslav.movieguide.domain.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Viacheslav Avd on 11.01.2023
 */

const val MAX_PAGES = 1000
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
    private val repository: MoviesRepository,
    private val coDispatchers: CoDispatchers,
) : ViewModel() {

    private val _movies = MutableStateFlow<List<MovieItemUi>>(emptyList())
    val movies: StateFlow<List<MovieItemUi>> = _movies.asStateFlow()

    private lateinit var allGenres: List<GenreDto>
    private var page: Int = 0

    init {
        viewModelScope.launch(coDispatchers.io) {
            val allGenresResult = repository.getGenres()
            if (allGenresResult is Success)
                allGenres = allGenresResult.data
            else
                return@launch
            getPopularMoviesNextPage()
        }
    }

    fun getPopularMoviesNextPage() {
        viewModelScope.launch(coDispatchers.io) {
            if (!::allGenres.isInitialized) return@launch
            if (++page > MAX_PAGES) {
                page = MAX_PAGES
                return@launch
            }
            val popularMovies = repository.getPopularMovies(page = page)
            if (popularMovies is Success) {
                _movies.update {
                    it.toMutableList()
                        .apply {
                            addAll(popularMovies.data.results
                                .map { moviesDto ->
                                    MovieItemUi.fromMovieDto(moviesDto, allGenres)
                                }
                            )
                        }.toList()
                }
            }
        }
    }
}