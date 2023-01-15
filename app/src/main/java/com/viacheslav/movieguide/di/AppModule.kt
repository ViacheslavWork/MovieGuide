package com.viacheslav.movieguide.di

import com.viacheslav.movieguide.core.CoDispatchers
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by Viacheslav Avd on 15.01.2023
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    abstract fun bindsCoDispatchers(dispatchers: CoDispatchers.Base): CoDispatchers
}