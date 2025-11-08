package com.jhs.data.remote

import com.jhs.data.model.DocumentEntity
import kotlinx.coroutines.flow.Flow

interface SearchRemoteDataSource {
    fun getDocuments(
        query: String,
        page: Int,
        size: Int,
        sort: String
    ): Flow<List<DocumentEntity>>
}