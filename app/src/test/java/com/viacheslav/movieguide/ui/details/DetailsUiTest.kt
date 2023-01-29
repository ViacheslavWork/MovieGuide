package com.viacheslav.movieguide.ui.details

import com.viacheslav.movieguide.data.dto.GenreDto
import com.viacheslav.movieguide.data.dto.MovieDetailsDto
import com.viacheslav.movieguide.di.MOVIES_IMAGE_URL
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Created by Viacheslav Avd on 20.01.2023
 */
class DetailsUiTest {

    private lateinit var movieDetailsDto: MovieDetailsDto
    private lateinit var actualDetailsUi: DetailsUi

    @Before
    fun `create initial dto`() {
        movieDetailsDto = MovieDetailsDto(
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
        actualDetailsUi = DetailsUi.fromDto(movieDetailsDto, castDto = emptyList(), "1")
    }

    @Test
    fun `test fields mapper dto to ui as is`() {
        assertEquals(1, actualDetailsUi.id)
        assertEquals(13, actualDetailsUi.ageLimit)
        assertEquals("Avengers: End Game", actualDetailsUi.title)
        assertEquals(100500, actualDetailsUi.numberOfReviews)
        assertEquals(
            "After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos' actions and restore balance to the universe.",
            actualDetailsUi.storyLine
        )
        assertEquals("1", actualDetailsUi.trailerYouTubeId)
    }

    @Test
    fun `test mapper number of stars`() {
        assertEquals(2, actualDetailsUi.numberOfStars)
    }

    @Test
    fun `test mapper genres`() {
        assertEquals("Thriller, Comedy, Battle", actualDetailsUi.genres)
    }

    @Test
    fun `test mapper backdrop path to picture path`() {
        assertEquals(MOVIES_IMAGE_URL + "backdropPath", actualDetailsUi.picturePath)
    }

    private fun getGenres() = listOf<GenreDto>(
        GenreDto("Thriller", 1),
        GenreDto("Comedy", 2),
        GenreDto("Battle", 3),
    )

    private fun getCast() = listOf<ActorUi>(
        ActorUi("Actor 1", "1"),
        ActorUi("Actor 2", "2"),
        ActorUi("Actor 3", "3"),
    )
}