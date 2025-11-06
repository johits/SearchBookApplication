package com.jhs.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jhs.local.model.BookmarkLocal
import com.jhs.local.room.constant.RoomConstant
import com.jhs.local.room.dao.BookmarkDao


@Database(
    entities = [BookmarkLocal::class],
    version = RoomConstant.ROOM_VERSION,
    exportSchema = false
)
@TypeConverters(DtoConverter::class)
abstract class BookmarkDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
}