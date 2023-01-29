package com.viacheslav.movieguide.ui.details

import com.viacheslav.movieguide.data.PopularMoviesPagingSource
import com.viacheslav.movieguide.data.Result
import com.viacheslav.movieguide.data.Result.*
import com.viacheslav.movieguide.data.dto.CastItemDto
import com.viacheslav.movieguide.data.dto.GenreDto
import com.viacheslav.movieguide.data.dto.MovieDetailsDto
import com.viacheslav.movieguide.data.dto.TrailerDto
import com.viacheslav.movieguide.domain.MoviesRepository

/**
 * Created by Viacheslav Avd on 28.01.2023
 */
internal class StubMoviesRepository(
    movieDetailsDto: MovieDetailsDto,
) : MoviesRepository {
    private var movieDetailsResult: Result<MovieDetailsDto> = Success(movieDetailsDto)
    private var movieCastResult: Result<List<CastItemDto>> = Success(emptyList())
    private var movieGenresResult: Result<List<GenreDto>> = Success(emptyList())

    fun setResultMovieDetailsDto(movieDetailsDto: MovieDetailsDto) {
        movieDetailsResult = Success(movieDetailsDto)
    }

    fun setErrorResultMovieDetailsDto() {
        movieDetailsResult = Failure()
    }

    fun setResultMovieCastDto(cast: List<CastItemDto>) {
        movieCastResult = Success(cast)
    }

    fun setErrorResultMovieCastDto() {
        movieCastResult = Failure()
    }

    fun setErrorResultMovieGenresDto() {
        movieGenresResult = Failure()
    }

    fun setResultMovieGenresDto(genres: List<GenreDto>) {
        movieGenresResult = Success(genres)
    }

    override suspend fun getMovie(movieId: Int) = movieDetailsResult

    override suspend fun getCast(movieId: Int) = movieCastResult

    override suspend fun getTrailers(movieId: Int) = Success(emptyList<TrailerDto>())

    override suspend fun getGenres() = movieGenresResult

    override fun getPopularMoviesPagingSource(): PopularMoviesPagingSource {
        TODO("Not yet implemented")
    }
}