package com.jhs.ui.main.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.jhs.presentation.detail.DetailContract
import com.jhs.presentation.model.BookDetailUiModel
import com.jhs.ui.main.util.toKoreanDate
import com.jhs.ui.main.util.toWon
import com.jhs.ui.main.components.text.InfoText
import com.jhs.ui.main.components.button.BookmarkToggleButton


@Composable
fun BookDetailScreen(
    state: DetailContract.State,
    sendEvent: (DetailContract.Event) -> Unit,
) {
    val scroll = rememberScrollState()
    Scaffold(
        topBar = {
            DetailTopBar(
                isBookmarked = state.item.isBookmarked,
                onBack = { sendEvent(DetailContract.Event.OnBack) },
                onClickBookmark = { isBookmark ->
                    sendEvent(
                        DetailContract.Event.OnToggleBookmark(
                            item = state.item,
                            isBookmark = isBookmark
                        )
                    )
                }
            )
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(inner)
                .padding(15.dp)
                .verticalScroll(scroll)
        ) {
            Text(
                text = state.item.title, style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )


            Row(modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)) {
                Box(
                    modifier = Modifier
                        .width(130.dp)
                        .height(180.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outlineVariant,
                            shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(state.item.thumbnail)          // 네트워크 URL
                            .build(),
                        contentDescription = "표지",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(Modifier.size(15.dp))
                Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    InfoText("저자", state.item.author.toString())
                    InfoText("출판사", state.item.publisher)
                    InfoText("출간일", state.item.pubDate.toKoreanDate())
                    InfoText("정상가", state.item.price.toWon())
                    state.item.salePrice.let { InfoText("할인가", it.toWon()) }
                }
            }
            if (state.item.description.isNotEmpty()) {
                Text(
                    text = "책 소개", style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.size(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(15.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(10.dp)
                ) {
                    Text(state.item.description)
                }
            }


        }
    }

}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopBar(
    isBookmarked: Boolean,
    onBack: () -> Unit,
    onClickBookmark: (Boolean) -> Unit
) {
    TopAppBar(
        title = { },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "뒤로")
            }
        },
        actions = {
            BookmarkToggleButton(
                bookmarkState = isBookmarked,
                onClickBookmark = { isBookmark ->
                    onClickBookmark(isBookmark)
                }
            )
        }
    )
}


@Composable
@Preview(showBackground = true)
fun PreBookDetailScreen() {
    val dummyState = DetailContract.State(
        item = BookDetailUiModel(
            title = "도서 제목",
            author = listOf("저자1", "저자2"),
            publisher = "출판사",
            pubDate = "2025-09-05T00:00:00.000+09:00",
            isbn = "1234567890",
            price = 15000,
            salePrice = 12000,
            isBookmarked = true,
            thumbnail = "",
            description = "이건 프리뷰용 설명이에요."
        ),
        isLoading = false,
        errorMessage = ""
    )

    BookDetailScreen(
        state = dummyState,
        sendEvent = {}
    )
}