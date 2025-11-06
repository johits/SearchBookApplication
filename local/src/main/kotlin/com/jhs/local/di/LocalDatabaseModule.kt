package com.jhs.local.di

import android.content.Context
import androidx.room.Room
import com.jhs.local.room.BookmarkDatabase
import com.jhs.local.room.constant.RoomConstant
import com.jhs.local.room.dao.BookmarkDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LocalDatabaseModule {

    @Singleton
    @Provides
    fun provideBookmarkDao(bookmarkDatabase: BookmarkDatabase): BookmarkDao = bookmarkDatabase.bookmarkDao()


    /**
     * Room DataBase
     */
    @Provides
    @Singleton
    fun provideRoomDataBase(@ApplicationContext context: Context): BookmarkDatabase {
        return Room.databaseBuilder(
            context,
            BookmarkDatabase::class.java,
            RoomConstant.ROOM_DB_NAME
        ).build()
    }
}