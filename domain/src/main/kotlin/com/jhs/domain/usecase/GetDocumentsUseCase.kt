package com.jhs.domain.usecase

import com.jhs.data_resource.DataResource
import com.jhs.domain.model.Document
import com.jhs.domain.repository.BookmarkRepository
import com.jhs.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

data class SearchResult(
    val documents: List<Document>,
    val bookmarkedIsbns: Set<String>
)

class GetDocumentsUseCase @Inject constructor(
    private val searchRepository: SearchRepository,
    private val bookmarkRepository: BookmarkRepository
) {
    operator fun invoke(query: String, sort: String): Flow<DataResource<SearchResult>> {
        val searchResultFlow: Flow<DataResource<List<Document>>> =
            searchRepository.getDocuments(query = query, sort = sort)
        val bookmarksFlow: Flow<DataResource<List<Document>>> =
            bookmarkRepository.getBookmarkedBookIds()

        return combine(
            searchResultFlow,
            bookmarksFlow
        ) { documentsRes: DataResource<List<Document>>, bookmarksRes: DataResource<List<Document>> ->
            when {
                documentsRes is DataResource.Loading || bookmarksRes is DataResource.Loading -> {
                    DataResource.Loading
                }

                documentsRes is DataResource.Error -> {
                    DataResource.Error(documentsRes.throwable)
                }

                bookmarksRes is DataResource.Error -> {
                    DataResource.Error(bookmarksRes.throwable)
                }

                documentsRes is DataResource.Success && bookmarksRes is DataResource.Success -> {
                    val bookmarkedIsbns = bookmarksRes.data.map { it.isbn }.toSet()
                    DataResource.Success(
                        SearchResult(
                            documents = documentsRes.data,
                            bookmarkedIsbns = bookmarkedIsbns
                        )
                    )
                }

                else -> {
                    DataResource.Error(IllegalStateException("Unexpected state"))
                }
            }
        }.catch { e ->
            emit(DataResource.Error(e))
        }
    }
}