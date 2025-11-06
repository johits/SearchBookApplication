package com.jhs.ui.main.components.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.jhs.presentation.model.BookDetailUiModel
import com.jhs.ui.main.util.toWon
import com.jhs.ui.main.components.text.InfoText
import com.jhs.ui.main.components.button.BookmarkToggleButton


@Composable
fun BookItem(
    model: BookDetailUiModel,
    onClickBookmark: (Boolean) -> Unit,
    onClickItem: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape(15.dp),
        onClick = onClickItem
    ) {
        Box(modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 10.dp, end = 15.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 96.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(model.thumbnail)
                        .build(),
                    contentDescription = "표지",
                    modifier = Modifier
                        .size(width = 90.dp, height = 120.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentScale = ContentScale.Crop
                )

                Spacer(Modifier.size(12.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 48.dp)
                ) {
                    Text(
                        "도서",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1
                    )
                    Spacer(Modifier.size(2.dp))
                    Text(
                        model.title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    InfoText("출판사", model.publisher)
                    InfoText("저자", model.author.toString())
                }
            }
            BookmarkToggleButton(
                bookmarkState = model.isBookmarked,
                onClickBookmark = { isBookmark ->
                    onClickBookmark(isBookmark)
                }
            )
            Spacer(Modifier.size(2.dp))
            Text(
                text = model.price.toWon(),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                modifier = Modifier.align(Alignment.BottomEnd)
            )

        }

    }


}

@Preview
@Composable
fun PreBookItem() {
    var isFavorite by remember { mutableStateOf(false) }
    BookItem(
        model = BookDetailUiModel(
            title = "도서 제목",
            author = listOf("저자"),
            publisher = "출판사",
            pubDate = "출간일",
            isbn = "ISBN",
            price = 15000,
            salePrice = 12000,
            isBookmarked = false,
//            coverUrl = "",
            thumbnail = "data:image/jpeg;base64,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",
            description = ""
        ),
        onClickItem = {},
        onClickBookmark = { isFavorite = !isFavorite })
}