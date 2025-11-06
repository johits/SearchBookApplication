package com.jhs.data.repository.impl

import com.jhs.data.local.BookmarkLocalDataSource
import com.jhs.data.model.toEntity
import com.jhs.data_resource.DataResource
import com.jhs.domain.model.Document
import com.jhs.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject


class BookmarkRepositoryImpl @Inject constructor(
    private val bookmarkDataSource: BookmarkLocalDataSource
) : BookmarkRepository {
    override fun getBookmarkedBookIds(): Flow<DataResource<List<Document>>> =
        bookmarkDataSource
            .getBookmarkedBookIds()
            .map { entities ->
                entities.map { it.toDomain() }
            }
            .map { documents ->
                DataResource.Success(documents) as DataResource<List<Document>>
            }
            .onStart { emit(DataResource.Loading) }
            .catch { e -> emit(DataResource.Error(e)) }


    override suspend fun bookmarkBook(document: Document, bookmark: Boolean) {
        bookmarkDataSource.bookmarkBook(document.toEntity(), bookmark)
    }

    override suspend fun insertBookmarkId(document: Document) {
        return bookmarkDataSource.insertBookmarkId(document.toEntity())
    }

    override suspend fun updateBookmarkId(document: Document) {
        return bookmarkDataSource.updateBookmarkId(document.toEntity())
    }

    override suspend fun deleteBookmark(isbn: String) {
        return bookmarkDataSource.deleteBookmark(isbn = isbn)
    }

    override suspend fun deleteAllBookmark() {
        return bookmarkDataSource.deleteAllBookmark()
    }

}