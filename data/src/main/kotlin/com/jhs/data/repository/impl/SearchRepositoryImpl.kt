package com.jhs.data.repository.impl

import com.jhs.data.remote.SearchRemoteDataSource
import com.jhs.data_resource.DataResource
import com.jhs.domain.model.Document
import com.jhs.domain.repository.SearchRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

internal class SearchRepositoryImpl @Inject constructor(
    private val searchRemoteDataSource: SearchRemoteDataSource,
) : SearchRepository {
    override fun getDocuments(query: String, sort: String) =
        searchRemoteDataSource.getDocuments(query, 1, 20, sort).map { entities ->
            entities.map { it.toDomain() }
        }
            .map { documents ->
                DataResource.Success(documents) as DataResource<List<Document>>
            }
            .onStart { emit(DataResource.Loading) }
            .catch { e ->
                emit(DataResource.Error(e))
            }
}