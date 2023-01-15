package com.viacheslav.movieguide.data

import com.viacheslav.movieguide.data.dto.CastItemDto
import com.viacheslav.movieguide.data.dto.GenresDto
import com.viacheslav.movieguide.data.dto.MovieDetailsDto
import com.viacheslav.movieguide.data.dto.MovieListItemDto
import com.viacheslav.movieguide.data.retrofit.MoviesGuideApiService
import com.viacheslav.movieguide.domain.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject

/**
 * Created by Viacheslav Avd on 12.01.2023
 */
class MoviesRepositoryImpl @Inject constructor(private val api: MoviesGuideApiService) :
    MoviesRepository {

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

    fun getGenresFlow(): Flow<ResponseObject<GenresDto>> {
        return flow {
            repeat(5) {
                emit(ResponseObject.Loading())
                kotlinx.coroutines.delay(1000)
            }
            try {
                throw IOException()
                emit(ResponseObject.Success(api.getGenres()))
            } catch (e: IOException) {
                emit(ResponseObject.Failure(500))
            }
        }.flowOn(Dispatchers.IO)
    }
}