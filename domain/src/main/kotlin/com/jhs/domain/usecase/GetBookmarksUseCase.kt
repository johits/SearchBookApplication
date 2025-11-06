package com.jhs.domain.usecase

import com.jhs.domain.repository.BookmarkRepository
import javax.inject.Inject


class GetBookmarksUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) {
    operator fun invoke() = bookmarkRepository.getBookmarkedBookIds()

}