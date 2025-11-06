package com.jhs.data.remote

import com.jhs.data.model.DocumentEntity

interface SearchRemoteDataSource {
    suspend fun getDocuments(
        query: String,
        page: Int,
        size: Int,
        sort: String
    ): List<DocumentEntity>
}