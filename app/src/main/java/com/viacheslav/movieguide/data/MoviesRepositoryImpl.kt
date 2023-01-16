package com.viacheslav.movieguide.data

import com.viacheslav.movieguide.data.Result.Failure
import com.viacheslav.movieguide.data.Result.Success
import com.viacheslav.movieguide.data.dto.CastItemDto
import com.viacheslav.movieguide.data.dto.GenreDto
import com.viacheslav.movieguide.data.retrofit.MoviesGuideApiService
import com.viacheslav.movieguide.domain.MoviesRepository
import javax.inject.Inject

/**
 * Created by Viacheslav Avd on 12.01.2023
 */
class MoviesRepositoryImpl @Inject constructor(
    private val api: MoviesGuideApiService,
    private val networkRequester: NetworkRequester,
    private val popularMoviesPagingSource: PopularMoviesPagingSource
) : MoviesRepository {
    override fun getPopularMoviesPagingSource() = popularMoviesPagingSource

    override suspend fun getMovie(movieId: Int) =
        networkRequester.makeRequest { api.getMovie(movieId) }

    override suspend fun getCast(movieId: Int): Result<List<CastItemDto>> =
        when (val credits = networkRequester.makeRequest { api.getCredits(movieId) }) {
            is Success -> Success(credits.data.cast)
            is Failure -> Failure(credits.code)
        }

    override suspend fun getGenres(): Result<List<GenreDto>> {
        val result = when (val genres = networkRequester.makeRequest { api.getGenres() }) {
            is Success -> Success(genres.data.genres)
            is Failure -> Failure(genres.code)
        }
        return result
    }
}

