package com.viacheslav.movieguide.data.retrofit

import com.viacheslav.movieguide.data.dto.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Viacheslav Avd on 11.01.2023
 */
interface MoviesGuideApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int
    ): Response<ListDto<MovieListItemDto>>

    @GET("movie/{movie_id}")
    suspend fun getMovie(@Path("movie_id") movie_id: Int): Response<MovieDetailsDto>

    @GET("movie/{movie_id}/videos")
    suspend fun getVideos(@Path("movie_id") movie_id: Int): Response<TrailersListDto>

    @GET("movie/{movie_id}/credits")
    suspend fun getCredits(@Path("movie_id") movie_id: Int): Response<CreditsDto>

    @GET("genre/movie/list")
    suspend fun getGenres(): Response<GenresDto>

    @GET("genre/movie/list")
    suspend fun getGenresResponse(): Response<GenresDto>

    /*   @GET("search/movie/")
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