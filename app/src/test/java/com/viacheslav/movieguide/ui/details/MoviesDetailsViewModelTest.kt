@file:OptIn(ExperimentalCoroutinesApi::class)

package com.viacheslav.movieguide.ui.details

import com.viacheslav.movieguide.data.Result
import com.viacheslav.movieguide.data.dto.CastItemDto
import com.viacheslav.movieguide.data.dto.GenreDto
import com.viacheslav.movieguide.data.dto.MovieDetailsDto
import com.viacheslav.movieguide.di.MOVIES_IMAGE_URL
import com.viacheslav.movieguide.utils.viewModelTestingRules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

/**
 * Created by Viacheslav Avd on 28.01.2023
 */
@OptIn(ExperimentalCoroutinesApi::class)
class MoviesDetailsViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = viewModelTestingRules()

    private val movieDetailsDto = MovieDetailsDto(
        id = 1,
        title = "Avengers: End Game",
        backdropPath = "backdropPath",
        genres = getGenres(),
        voteCount = 100500,
        overview = "After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos' actions and restore balance to the universe.",
        runtime = 102,
        voteAverage = 4.8f,
        adult = false,
    )

    @Test
    fun `movieDetailsState by default return success(details)`() = runTest {
        val expectedDetailsUiState = MovieDetailsState.MovieLoaded(
            DetailsUi(
                id = 1,
                ageLimit = 13,
                title = "Avengers: End Game",
                genres = "Thriller, Comedy, Battle",
                numberOfStars = 2,
                numberOfReviews = 100500,
                cast = getActors(),
                picturePath = MOVIES_IMAGE_URL + "backdropPath",
                storyLine = "After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos' actions and restore balance to the universe.",
                trailerYouTubeId = null
            )
        )
        val repository = StubMoviesRepository(movieDetailsDto)
        repository.setResultMovieCastDto(getCast())
        val viewModel = MoviesDetailsViewModel(repository)
        viewModel.getMovie(1)

        advanceUntilIdle()

        val actualDetailsUiState = viewModel.movieDetailsState.value

        assertEquals(
            expectedDetailsUiState.movieDetailsUi,
            (actualDetailsUiState as MovieDetailsState.MovieLoaded).movieDetailsUi
        )
    }

    @Test
    fun `movieDetailsState on error movieDetailsDto returns failure`() = runTest {
        val repository = StubMoviesRepository(movieDetailsDto)
        repository.setErrorResultMovieDetailsDto()
        val viewModel = MoviesDetailsViewModel(repository)
        viewModel.getMovie(1)
        advanceUntilIdle()

        assertEquals(MovieDetailsState.FailedToLoad, viewModel.movieDetailsState.value)
    }

    @Test
    fun `movieDetailsState on error movieCastDto returns failure`() = runTest {
        val repository = StubMoviesRepository(movieDetailsDto)
        repository.setErrorResultMovieCastDto()
        val viewModel = MoviesDetailsViewModel(repository)
        viewModel.getMovie(1)
        advanceUntilIdle()

        assertEquals(MovieDetailsState.FailedToLoad, viewModel.movieDetailsState.value)
    }

    private fun getGenres() = listOf<GenreDto>(
        GenreDto("Thriller", 1),
        GenreDto("Comedy", 2),
        GenreDto("Battle", 3),
    )

    private fun getCast() = listOf<CastItemDto>(
        CastItemDto(1, name = "Actor 1"),
        CastItemDto(2, name = "Actor 2"),
        CastItemDto(3, name = "Actor 3"),
    )

    private fun getActors() = listOf<ActorUi>(
        ActorUi("Actor 1", MOVIES_IMAGE_URL + "null"),
        ActorUi("Actor 2", MOVIES_IMAGE_URL + "null"),
        ActorUi("Actor 3", MOVIES_IMAGE_URL + "null"),
    )
}
