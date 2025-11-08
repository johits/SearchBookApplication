package com.jhs.remote.impl

import com.jhs.data.model.DocumentEntity
import com.jhs.data.remote.SearchRemoteDataSource
import com.jhs.remote.api.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : SearchRemoteDataSource {
    override fun getDocuments(
        query: String,
        page: Int,
        size: Int,
        sort: String
    ): Flow<List<DocumentEntity>> = flow {
        val documents = apiService.getBookData(
            query = query,
            page = page,
            size = size,
            sort = sort
        ).documents.map { it.toData() }
        emit(documents)
    }
}