package com.viacheslav.movieguide.domain

import com.viacheslav.movieguide.data.Result
import com.viacheslav.movieguide.data.dto.*

/**
 * Created by Viacheslav Avd on 12.01.2023
 */
interface MoviesRepository {
    suspend fun getMovie(movieId: Int): Result<MovieDetailsDto>
    suspend fun getCast(movieId: Int): Result<List<CastItemDto>>
    suspend fun getGenres(): Result<List<GenreDto>>
    suspend fun getPopularMovies(page: Int): Result<ListDto<MovieListItemDto>>
}