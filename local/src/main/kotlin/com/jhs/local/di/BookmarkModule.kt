package com.jhs.local.di

import com.jhs.data.local.BookmarkLocalDataSource
import com.jhs.local.impl.BookmarkLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal abstract class BookmarkModule {

    @Binds
    @Singleton
    abstract fun bindMovieLocalDataSource(source: BookmarkLocalDataSourceImpl): BookmarkLocalDataSource

}
