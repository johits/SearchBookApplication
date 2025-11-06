package com.jhs.domain.usecase

import com.jhs.domain.repository.SearchRepository
import javax.inject.Inject

class GetDocumentsUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    operator fun invoke(query: String, sort: String) =
        searchRepository.getDocuments(query = query, sort = sort)
}