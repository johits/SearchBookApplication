package com.jhs.domain.repository

import com.jhs.data_resource.DataResource
import com.jhs.domain.model.Document
import kotlinx.coroutines.flow.Flow


interface BookmarkRepository {
    fun getBookmarkedBookIds(): Flow<DataResource<List<Document>>>
    suspend fun bookmarkBook(document: Document, bookmark: Boolean)
    suspend fun insertBookmarkId(document: Document)
    suspend fun updateBookmarkId(document: Document)
    suspend fun deleteBookmark(isbn: String)
    suspend fun deleteAllBookmark()
}