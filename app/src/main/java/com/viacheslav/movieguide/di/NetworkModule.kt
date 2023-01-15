package com.viacheslav.movieguide.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.viacheslav.movieguide.data.retrofit.MoviesGuideApiService
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
import javax.inject.Singleton

const val API_KEY = "822cc7b4b405cf6db0f2b7051939a7ca"

//const val API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4MjJjYzdiNGI0MDVjZjZkYjBmMmI3MDUxOTM5YTdjYSIsInN1YiI6IjYxNmU2NzI1MTYwZTczMDA4ZWZkNGVmNCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.GxMSJrtOSbwDQ_u8OJrJTzjGx800pk_sqzi4Cq306os"
const val BASE_URL = "https://api.themoviedb.org/3/"
const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Singleton
    @Provides
    fun okHttp() = OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(MoviesApiHeaderInterceptor())
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    @Singleton
    @Provides
    fun provideAPIService(okHttpClient: OkHttpClient): MoviesGuideApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
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
                originalRequest.url.newBuilder().addQueryParameter("api_key", API_KEY).build()

            val request = originalRequest.newBuilder()
                .url(originalHttpUrl)
                .build()
            return chain.proceed(request)
        }
    }
}
