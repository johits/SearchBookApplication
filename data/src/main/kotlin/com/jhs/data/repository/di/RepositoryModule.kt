package com.jhs.data.repository.di

import com.jhs.data.repository.impl.SearchRepositoryImpl
import com.jhs.data.repository.impl.BookmarkRepositoryImpl
import com.jhs.domain.repository.SearchRepository
import com.jhs.domain.repository.BookmarkRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindBookRepository(repo: SearchRepositoryImpl): SearchRepository

    @Binds
    @Singleton
    abstract fun bindBookmarkRepository(repo: BookmarkRepositoryImpl): BookmarkRepository
}