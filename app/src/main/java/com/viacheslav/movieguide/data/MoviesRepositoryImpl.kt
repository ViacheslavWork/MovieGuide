package com.viacheslav.movieguide.data

import com.viacheslav.movieguide.data.dto.CastItemDto
import com.viacheslav.movieguide.data.dto.MovieDetailsDto
import com.viacheslav.movieguide.data.dto.MovieListItemDto
import com.viacheslav.movieguide.data.retrofit.MoviesGuideApiService
import com.viacheslav.movieguide.domain.MoviesRepository

/**
 * Created by Viacheslav Avd on 12.01.2023
 */
class MoviesRepositoryImpl(private val api: MoviesGuideApiService) : MoviesRepository {

    override suspend fun getPopularMovies(): List<MovieListItemDto> {
        return api.getPopularMovies().results
    }

    override suspend fun getMovie(movieId: Int): MovieDetailsDto {
        return api.getMovie(movieId)
    }

    override suspend fun getCast(movieId: Int): List<CastItemDto> {
        return api.getCredits(movieId).cast
    }

    override suspend fun getGenres() = api.getGenres().genres
}