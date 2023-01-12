package com.viacheslav.movieguide.ui.details

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

class MoviesDetailsViewModel : ViewModel() {

    private val repository = MoviesRepositoryImpl(moviesGuideApiService)

    private val _movie = MutableStateFlow<DetailsUi?>(null)
    val movie: StateFlow<DetailsUi?> = _movie.asStateFlow()

    fun getMovie(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _movie.update {
                DetailsUi.fromDto(
                    movieDetailsDto = repository.getMovie(movieId = id),
                    castDto = repository.getCast(movieId = id)
                )
            }
        }
    }
}