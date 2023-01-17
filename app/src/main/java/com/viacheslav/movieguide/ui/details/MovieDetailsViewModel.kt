package com.viacheslav.movieguide.ui.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viacheslav.movieguide.core.CoDispatchers
import com.viacheslav.movieguide.data.Result
import com.viacheslav.movieguide.domain.MoviesRepository
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
    private val repository: MoviesRepository,
    private val coDispatchers: CoDispatchers
) : ViewModel() {
    private val _movie = MutableStateFlow<DetailsUi?>(null)
    val movie: StateFlow<DetailsUi?> = _movie.asStateFlow()

    fun getMovie(id: Int) {
        if (movie.value != null) return
        Log.d(TAG, "getMovie: $id")
        viewModelScope.launch(coDispatchers.io) {
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

            if (movie is Result.Success && cast is Result.Success) {
                _movie.update {
                    DetailsUi.fromDto(
                        movieDetailsDto = movie.data,
                        castDto = cast.data,
                        trailer?.key
                    )
                }
            }
        }
    }
}