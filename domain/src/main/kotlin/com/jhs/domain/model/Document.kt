package com.jhs.domain.model

data class  Document(
    val title: String,
    val contents: String,
    val thumbnail: String,
    val authors: List<String>,
    val publisher: String,
    val datetime: String,
    val isbn: String,
    val price: Int,
    val salePrice: Int
)
