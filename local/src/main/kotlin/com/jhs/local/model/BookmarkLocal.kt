package com.jhs.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jhs.data.model.DocumentEntity
import com.jhs.local.model.mapper.LocalMapper
import com.jhs.local.room.constant.RoomConstant

@Entity(tableName = RoomConstant.Table.BOOKMARK)
data class BookmarkLocal(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val title: String, //도서 제목
    val authors: List<String>, //저자
    val contents: String, //내용
    @ColumnInfo(name = "isbn")
    val isbn: String, //ISBN10(10자리) 또는 ISBN13(13자리) 형식의 국제 표준 도서번호
    val publisher: String, //출판사
    val price: Int, //도서 정가
    val salePrice: Int, //도서 판매가
    val thumbnail: String, //도서 표지 미리보기 URL
    val dateTime: String,
) : LocalMapper<DocumentEntity> {
    override fun toData(): DocumentEntity =
        DocumentEntity(
            title = title,
            contents = contents,
            thumbnail = thumbnail,
            authors = authors,
            publisher = publisher,
            datetime = dateTime,
            isbn = isbn,
            price = price,
            salePrice = salePrice
        )
}

fun DocumentEntity.toLocal(): BookmarkLocal =
    BookmarkLocal(
        title = title,
        authors = authors,
        contents = contents,
        isbn = isbn,
        publisher = publisher,
        price = price,
        salePrice = salePrice,
        thumbnail = thumbnail,
        dateTime = datetime
    )
