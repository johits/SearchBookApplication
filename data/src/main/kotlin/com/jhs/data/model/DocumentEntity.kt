package com.jhs.data.model

import com.jhs.data.model.mapper.DataMapper
import com.jhs.domain.model.Document

data class DocumentEntity(
    val title: String,
    val contents: String,
    val thumbnail: String,
    val authors: List<String>,
    val publisher: String,
    val datetime: String,
    val isbn: String,
    val price: Int,
    val salePrice: Int
) : DataMapper<Document> {
    override fun toDomain(): Document =
        Document(
            title = title,
            contents = contents,
            thumbnail = thumbnail,
            authors = authors,
            publisher = publisher,
            datetime = datetime,
            isbn = isbn,
            price = price,
            salePrice = salePrice
        )
}

fun Document.toEntity(): DocumentEntity = DocumentEntity(
    title = title,
    contents = contents,
    thumbnail = thumbnail,
    authors = authors,
    publisher = publisher,
    datetime = datetime,
    isbn = isbn,
    price = price,
    salePrice = salePrice
)