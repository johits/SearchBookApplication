package com.jhs.local.impl

import com.jhs.data.local.BookmarkLocalDataSource
import com.jhs.data.model.DocumentEntity
import com.jhs.local.model.toLocal
import com.jhs.local.room.dao.BookmarkDao
import com.jhs.local.model.mapper.toData

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class BookmarkLocalDataSourceImpl @Inject constructor(
    private val bookmarkDao: BookmarkDao
) : BookmarkLocalDataSource {
    override fun getBookmarkedBookIds(): Flow<List<DocumentEntity>> {
        return bookmarkDao.getBookmarks().map { it.toData() }
    }

    override suspend fun bookmarkBook(book: DocumentEntity, bookmark: Boolean) {
        if (bookmark) {
            bookmarkDao.insertBookmarkId(book.toLocal())
        } else {
            bookmarkDao.deleteBookmark(isbn = book.isbn)
        }
    }

    override suspend fun insertBookmarkId(bookmark: DocumentEntity) {
        bookmarkDao.insertBookmarkId(bookmark.toLocal())
    }

    override suspend fun updateBookmarkId(bookmark: DocumentEntity) {
        bookmarkDao.updateBookmarkId(bookmark.toLocal())
    }

    override suspend fun deleteBookmark(isbn: String) {
        bookmarkDao.deleteBookmark(isbn)
    }


    override suspend fun deleteAllBookmark() {
        bookmarkDao.deleteAll()
    }
}