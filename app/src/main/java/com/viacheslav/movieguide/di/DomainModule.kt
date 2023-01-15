package com.viacheslav.movieguide.di

import com.viacheslav.movieguide.data.MoviesRepositoryImpl
import com.viacheslav.movieguide.domain.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * Created by Viacheslav Avd on 15.01.2023
 */
@Module
@InstallIn(ViewModelComponent::class)
abstract class DomainModule {
    @Binds
    abstract fun bindMoviesRepository(moviesRepository: MoviesRepositoryImpl): MoviesRepository
}