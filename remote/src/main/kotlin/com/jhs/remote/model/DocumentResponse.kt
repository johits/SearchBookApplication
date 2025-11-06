package com.jhs.remote.model

import com.google.gson.annotations.SerializedName
import com.jhs.data.model.DocumentEntity
import com.jhs.remote.model.mapper.RemoteMapper

data class DocumentResponse(
    val title: String,
    val contents: String,
    val thumbnail: String,
    val authors: List<String>,
    val publisher: String,
    val datetime: String,
    val isbn: String,
    val price: Int,
    @SerializedName("sale_price")
    val salePrice: Int
) : RemoteMapper<DocumentEntity> {
    override fun toData(): DocumentEntity =
        DocumentEntity(
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
