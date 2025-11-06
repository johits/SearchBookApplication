package com.jhs.remote.di

import com.jhs.data.remote.SearchRemoteDataSource
import com.jhs.remote.impl.SearchRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RemoteDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindRemoteDataSource(source: SearchRemoteDataSourceImpl): SearchRemoteDataSource
}