package com.jhs.data.local

import com.jhs.data.model.DocumentEntity
import kotlinx.coroutines.flow.Flow


interface BookmarkLocalDataSource {
    fun getBookmarkedBookIds(): Flow<List<DocumentEntity>>
    suspend fun bookmarkBook(book: DocumentEntity, bookmark: Boolean)
    suspend fun insertBookmarkId(bookmark: DocumentEntity)
    suspend fun updateBookmarkId(bookmark: DocumentEntity)
    suspend fun deleteBookmark(isbn: String)
    suspend fun deleteAllBookmark()
}