package com.viacheslav.movieguide.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viacheslav.movieguide.data.Result
import com.viacheslav.movieguide.domain.MoviesRepository
import com.viacheslav.movieguide.ui.details.MovieDetailsState.*
import dagger.hilt.android.lifecycle.HiltViewModel
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

private const val TAG = "MovieDetailsViewModel"

@HiltViewModel
class MoviesDetailsViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {
    private val _movieDetailsState = MutableStateFlow<MovieDetailsState>(Initial)
    val movieDetailsState: StateFlow<MovieDetailsState> = _movieDetailsState.asStateFlow()

    fun getMovie(id: Int) {
//        Log.d(TAG, "getMovie: $id")
        viewModelScope.launch {
            val movieDeferred = async { repository.getMovie(id) }
            val castDeferred = async { repository.getCast(id) }
            val trailersDeferred = async { repository.getTrailers(id) }

            val movie = movieDeferred.await()
            val cast = castDeferred.await()
            val trailer = trailersDeferred.await().let { result ->
                if (result is Result.Success)
                    result.data.maxByOrNull { it.official }
                else
                    null
            }

            _movieDetailsState.update {
                if (movie is Result.Success && cast is Result.Success) {
                    MovieLoaded(
                        DetailsUi.fromDto(
                            movieDetailsDto = movie.data,
                            castDto = cast.data,
                            trailerId = trailer?.key
                        )
                    )
                } else
                    FailedToLoad
            }
        }
    }
}
