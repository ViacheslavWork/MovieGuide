package com.viacheslav.movieguide.domain

import com.viacheslav.movieguide.data.dto.CastItemDto
import com.viacheslav.movieguide.data.dto.GenreDto
import com.viacheslav.movieguide.data.dto.MovieDetailsDto
import com.viacheslav.movieguide.data.dto.MovieListItemDto

/**
 * Created by Viacheslav Avd on 12.01.2023
 */
interface MoviesRepository {
    suspend fun getPopularMovies(): List<MovieListItemDto>
    suspend fun getMovie(movieId: Int): MovieDetailsDto
    suspend fun getCast(movieId: Int): List<CastItemDto>
    suspend fun getGenres(): List<GenreDto>
}