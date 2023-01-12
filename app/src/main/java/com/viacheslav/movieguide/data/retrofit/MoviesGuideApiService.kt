package com.viacheslav.movieguide.data.retrofit

import com.viacheslav.movieguide.data.dto.ListResponse
import com.viacheslav.movieguide.data.dto.MovieDetailsDto
import com.viacheslav.movieguide.data.dto.MovieListItemDto
import com.viacheslav.movieguide.data.dto.ResponseCredits
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Viacheslav Avd on 11.01.2023
 */
interface MoviesGuideApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(): ListResponse<MovieListItemDto>

    @GET("movie/{movie_id}")
    suspend fun getMovie(@Path("movie_id") movie_id: Int): MovieDetailsDto

    @GET("movie/{movie_id}/credits")
    suspend fun getCredits(@Path("movie_id") movie_id: Int): ResponseCredits

/*    @GET("genre/movie/list")
    suspend fun getGenres(@Query("api_key") apiKey: String): GenreResponse

    @GET("search/movie/")
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("include_adult") include_adult: Boolean = false
    ): MoviesResponse*/


    @GET("authentication/token/new")
    suspend fun getToken(): Any

    @GET("account")
    suspend fun getAccount(): Any
}