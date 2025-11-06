package com.jhs.domain.usecase

import com.jhs.domain.model.Document
import com.jhs.domain.repository.BookmarkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class UpdateBookmarkUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) {

    suspend operator fun invoke(document: Document, isBookmark: Boolean) {
        withContext(Dispatchers.IO) {
            if (isBookmark) {
                bookmarkRepository.insertBookmarkId(document)
            } else {
                bookmarkRepository.deleteBookmark(isbn = document.isbn)
            }
        }

    }
}
