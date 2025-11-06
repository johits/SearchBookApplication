package com.jhs.ui.main.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jhs.presentation.model.BookDetailUiModel
import com.jhs.presentation.search.SearchContract
import com.jhs.ui.main.components.input.SearchHeader
import com.jhs.ui.main.components.item.BookItem
import com.jhs.ui.main.components.layout.SortAndFilterRow
import com.jhs.ui.main.navigation.Destination


@Composable
internal fun SearchBookScreen(
    state: SearchContract.State,
    sendEvent: (SearchContract.Event) -> Unit,
) {
    var query by rememberSaveable { mutableStateOf(state.query) }
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            SearchHeader(
                type = Destination.Search,
                query = query,
                onQueryChange = { query = it },
                onSearch = {
                    if (query.isNotBlank()) {
                        sendEvent(SearchContract.Event.OnSearch(query, state.currentSort))
                    }
                }
            )
            if (state.items.isNotEmpty()) {
                SortAndFilterRow(
                    type = Destination.Search,
                    selectedSort = state.currentSort,
                    onSortSelected = { sort ->
                        sendEvent(SearchContract.Event.OnSearch(query, sort))
                    }
                )
            }
        }

        items(
            items = state.items,
        ) { book ->
            BookItem(
                model = book,
                onClickBookmark = { isBookmark ->
                    sendEvent(
                        SearchContract.Event.OnToggleBookmark(
                            item = book,
                            isBookmark = isBookmark
                        )
                    )
                },
                onClickItem = { sendEvent(SearchContract.Event.OnClickItem(book)) }
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchBookScreen() {

    val dummyState = SearchContract.State(
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
                thumbnail = "",   // 썸네일 없으면 BookItem에서 알아서 처리하게
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

    SearchBookScreen(
        state = dummyState,
        sendEvent = { }
    )
}
