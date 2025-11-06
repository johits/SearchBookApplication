package com.jhs.remote.impl

import com.jhs.data.model.DocumentEntity
import com.jhs.data.remote.SearchRemoteDataSource
import com.jhs.remote.api.ApiService
import javax.inject.Inject

class SearchRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : SearchRemoteDataSource {
    override suspend fun getDocuments(
        query: String,
        page: Int,
        size: Int,
        sort: String
    ): List<DocumentEntity> {
        return apiService.getBookData(
            query = query,
            page = page,
            size = size,
            sort = sort
        ).documents.map {
            it.toData()
        }
    }
}