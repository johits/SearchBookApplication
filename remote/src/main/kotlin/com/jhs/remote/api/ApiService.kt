package com.jhs.remote.api

import com.jhs.remote.model.BooksResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v3/search/book")
    suspend fun getBookData(
        @Query("query") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): BooksResponse

}