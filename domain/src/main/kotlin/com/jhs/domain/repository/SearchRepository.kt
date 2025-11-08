package com.jhs.domain.repository

import com.jhs.data_resource.DataResource
import com.jhs.domain.model.Document
import kotlinx.coroutines.flow.Flow


interface SearchRepository {
    fun getDocuments(query: String, sort: String): Flow<DataResource<List<Document>>>
}
