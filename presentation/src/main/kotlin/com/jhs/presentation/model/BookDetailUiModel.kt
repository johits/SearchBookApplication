package com.jhs.presentation.model

import android.os.Parcelable
import com.jhs.domain.model.Document
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookDetailUiModel(
    val title: String,
    val author: List<String>,
    val publisher: String,
    val pubDate: String,
    val isbn: String,
    val price: Int,
    val salePrice: Int,
    val isBookmarked: Boolean,
    val thumbnail: String?,
    val description: String
) : Parcelable {
    companion object {
        val EMPTY = BookDetailUiModel(
            title = "",
            author = emptyList(),
            publisher = "",
            pubDate = "",
            isbn = "",
            price = 0,
            salePrice = 0,
            isBookmarked = false,
            thumbnail = "",
            description = ""
        )
    }
}

fun Document.toPresentation(): BookDetailUiModel =
    BookDetailUiModel(
        title = title,
        author = authors,
        publisher = publisher,
        pubDate = datetime,
        isbn = isbn,
        price = price,
        salePrice = salePrice,
        isBookmarked = false,
        thumbnail = thumbnail,
        description = contents
    )

fun BookDetailUiModel.toDomain() = Document(
    title = title,
    contents = description,
    thumbnail = thumbnail.orEmpty(),
    authors = author,
    publisher = publisher,
    datetime = pubDate,
    isbn = isbn,
    price = price,
    salePrice = salePrice
)
