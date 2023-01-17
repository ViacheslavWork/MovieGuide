package com.viacheslav.movieguide.domain

import com.viacheslav.movieguide.data.PopularMoviesPagingSource
import com.viacheslav.movieguide.data.Result
import com.viacheslav.movieguide.data.dto.CastItemDto
import com.viacheslav.movieguide.data.dto.GenreDto
import com.viacheslav.movieguide.data.dto.MovieDetailsDto
import com.viacheslav.movieguide.data.dto.TrailerDto

/**
 * Created by Viacheslav Avd on 12.01.2023
 */
interface MoviesRepository {
    suspend fun getMovie(movieId: Int): Result<MovieDetailsDto>
    suspend fun getCast(movieId: Int): Result<List<CastItemDto>>
    suspend fun getTrailers(movieId: Int): Result<List<TrailerDto>>
    suspend fun getGenres(): Result<List<GenreDto>>
    fun getPopularMoviesPagingSource(): PopularMoviesPagingSource
}