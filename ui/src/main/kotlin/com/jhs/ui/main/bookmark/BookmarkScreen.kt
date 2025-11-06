package com.jhs.ui.main.bookmark

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jhs.presentation.bookmark.BookmarkContract
import com.jhs.presentation.model.BookDetailUiModel
import com.jhs.presentation.model.SortType
import com.jhs.ui.main.components.input.SearchHeader
import com.jhs.ui.main.components.item.BookItem
import com.jhs.ui.main.components.layout.SortAndFilterRow
import com.jhs.ui.main.navigation.Destination


@Composable
internal fun BookmarkScreen(
    state: BookmarkContract.State,
    sendEvent: (BookmarkContract.Event) -> Unit
) {
    var selectedSort by rememberSaveable { mutableStateOf(SortType.TITLE_ASC) }
    var showFilter by rememberSaveable { mutableStateOf(false) }
    var query by rememberSaveable { mutableStateOf(state.query) }


    if (!state.isLoading && state.filteredItems.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "요청하신 도서를 찾을 수 없습니다.",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = {
                        sendEvent(BookmarkContract.Event.OnPriceReset(0, 9_999_999))
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text("전체 리스트 보기")
                }
            }
        }
    } else {

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                SearchHeader(
                    type = Destination.Favorites,
                    query = query,
                    onQueryChange = { newQuery ->
                        query = newQuery
                    },
                    onSearch = { inputQuery ->
                        if (inputQuery.isNotBlank()) {
                            sendEvent(BookmarkContract.Event.OnSearchClicked(inputQuery))
                        }
                    }
                )
                if (state.filteredItems.isNotEmpty()) {
                    SortAndFilterRow(
                        type = Destination.Favorites,
                        selectedSort = selectedSort,
                        onSortSelected = { sort ->
                            selectedSort = sort
                            sendEvent(BookmarkContract.Event.OnSort(sort))
                        },
                        filterSlot = {
                            BookmarkFilterSlot(
                                showFilter = showFilter,
                                onShowFilterChange = { showFilter = it },
                                startPrice = state.startPrice,
                                endPrice = state.endPrice,
                                sendEvent = sendEvent
                            )
                        }
                    )
                }
            }
            items(
                items = state.filteredItems,
            ) { book ->
                BookItem(
                    model = book,
                    onClickBookmark = { isBookmark ->
                        sendEvent(
                            BookmarkContract.Event.OnToggleBookmark(
                                book,
                                isBookmark
                            )
                        )
                    },
                    onClickItem = { sendEvent(BookmarkContract.Event.OnClickItem(book)) }
                )

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreFavoritesScreen() {
    val dummyState = BookmarkContract.State(
        isLoading = false,
        query = "코틀린",
        items = listOf(
            BookDetailUiModel(
                title = "도서 제목",
                author = listOf("저자1", "저자2"),
                publisher = "출판사",
                pubDate = "2025-10-30",
                isbn = "1234567890",
                price = 15000,
                salePrice = 12000,
                isBookmarked = true,
                thumbnail = "",
                description = "이건 프리뷰용 설명이에요."
            ),
            BookDetailUiModel(
                title = "두 번째 책",
                author = listOf("다른 저자"),
                publisher = "출판사2",
                pubDate = "2025-09-10",
                isbn = "0987654321",
                price = 18000,
                salePrice = 15000,
                isBookmarked = false,
                thumbnail = "",
                description = "북마크 안 된 책"
            )
        )
    )

    BookmarkScreen(
        state = dummyState,
        sendEvent = {}
    )
}