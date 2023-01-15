package com.viacheslav.movieguide.ui.details

//import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viacheslav.movieguide.data.Result
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

//import javax.inject.Inject

/**
 * Created by Viacheslav Avd on 11.01.2023
 */
@HiltViewModel
class MoviesDetailsViewModel @Inject constructor(private val repository: MoviesRepository) :
    ViewModel() {

    private val _movie = MutableStateFlow<DetailsUi?>(null)
    val movie: StateFlow<DetailsUi?> = _movie.asStateFlow()

    fun getMovie(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val movieDeferred = async { repository.getMovie(id) }
            val castDeferred = async { repository.getCast(id) }
            val movie = movieDeferred.await()
            val cast = castDeferred.await()
            if (movie is Result.Success && cast is Result.Success) {
                _movie.update {
                    DetailsUi.fromDto(
                        movieDetailsDto = movie.data,
                        castDto = cast.data
                    )
                }
            }
        }
    }
}