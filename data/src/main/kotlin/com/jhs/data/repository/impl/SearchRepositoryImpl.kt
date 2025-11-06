package com.jhs.data.repository.impl

import com.jhs.data.remote.SearchRemoteDataSource
import com.jhs.domain.repository.SearchRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class SearchRepositoryImpl @Inject constructor(
    private val searchRemoteDataSource: SearchRemoteDataSource,
) : SearchRepository {
    override fun getDocuments(query: String, sort: String) =
        flow {
            val entities = searchRemoteDataSource.getDocuments(query, 1, 20, sort = sort)
            val domain = entities.map { it.toDomain() }
            emit(domain)
        }

}