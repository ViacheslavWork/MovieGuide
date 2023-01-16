package com.viacheslav.movieguide.ui.movies_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.viacheslav.movieguide.domain.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
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
) : ViewModel() {

    val moviesFlow: Flow<PagingData<MovieItemUi>> = Pager(
        PagingConfig(20),
        pagingSourceFactory = { repository.getPopularMoviesPagingSource() }
    ).flow.cachedIn(viewModelScope)
}