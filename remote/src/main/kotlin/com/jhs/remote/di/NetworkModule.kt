package com.jhs.remote.di

import com.jhs.remote.api.ApiService
import com.jhs.remote.api.createApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {


    @Provides
    @Singleton
    fun provideApiService(): ApiService = createApiService()


}
