package com.jhs.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jhs.local.model.BookmarkLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    @Insert
    fun insertBookmarkId(bookmarkLocal: BookmarkLocal)

    @Update
    fun updateBookmarkId(bookmarkLocal: BookmarkLocal)

    @Query("SELECT * FROM bookmark")
    fun getBookmarks(): Flow<List<BookmarkLocal>>

    @Query("DELETE FROM bookmark where isbn = :isbn")
    fun deleteBookmark(isbn: String)

    @Query("DELETE FROM bookmark")
    fun deleteAll()
}