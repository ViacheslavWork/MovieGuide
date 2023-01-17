package com.viacheslav.movieguide.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.viacheslav.movieguide.BuildConfig
import com.viacheslav.movieguide.data.retrofit.MoviesGuideApiService
import com.viacheslav.movieguide.data.retrofit.YouTubeApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


//const val API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4MjJjYzdiNGI0MDVjZjZkYjBmMmI3MDUxOTM5YTdjYSIsInN1YiI6IjYxNmU2NzI1MTYwZTczMDA4ZWZkNGVmNCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.GxMSJrtOSbwDQ_u8OJrJTzjGx800pk_sqzi4Cq306os"
const val YOU_TUBE_BASE_URL = "https://www.googleapis.com/youtube/v3/"
const val MOVIES_BASE_URL = "https://api.themoviedb.org/3/"
const val MOVIES_IMAGE_URL = "https://image.tmdb.org/t/p/w500"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Singleton
    @Named("movies_ok_http")
    @Provides
    fun moviesOkHttp(loggingInterceptor: HttpLoggingInterceptor) = OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(MoviesApiHeaderInterceptor())
        .addInterceptor(loggingInterceptor)
        .build()

    @Singleton
    @Named("you_tube_ok_http")
    @Provides
    fun youTubeOkHttp(loggingInterceptor: HttpLoggingInterceptor) = OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(YouTubeApiHeaderInterceptor())
        .addInterceptor(loggingInterceptor)
        .build()

    @Singleton
    @Provides
    fun provideMoviesApiService(@Named("movies_ok_http") okHttpClient: OkHttpClient): MoviesGuideApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(MOVIES_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit.create()
    }

    @Singleton
    @Provides
    fun provideYouTubeAPIService(@Named("you_tube_ok_http") okHttpClient: OkHttpClient): YouTubeApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(YOU_TUBE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit.create()
    }

    private class MoviesApiHeaderInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()
            // in this case the line is not wrapped
            val originalHttpUrl =
                originalRequest.url.newBuilder()
                    .addQueryParameter("api_key", BuildConfig.MOVIES_API_KEY).build()

            val request = originalRequest.newBuilder()
                .url(originalHttpUrl)
                .build()
            return chain.proceed(request)
        }
    }

    private class YouTubeApiHeaderInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()
            // in this case the line is not wrapped
            val originalHttpUrl =
                originalRequest.url.newBuilder()
                    .addQueryParameter("key", BuildConfig.YOU_TUBE_API_KEY).build()

            val request = originalRequest.newBuilder()
                .url(originalHttpUrl)
                .build()
            return chain.proceed(request)
        }
    }

    @Singleton
    @Provides
    fun provideLoginInterceptor() =
        HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        )
}
