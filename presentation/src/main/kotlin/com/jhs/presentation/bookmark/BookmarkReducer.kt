package com.jhs.presentation.bookmark

import com.jhs.presentation.model.BookDetailUiModel
import com.jhs.presentation.model.SortType
import javax.inject.Inject

class BookmarkReducer @Inject constructor() {
    fun reduce(
        state: BookmarkContract.State,
        event: BookmarkContract.Event
    ): Pair<BookmarkContract.State, List<BookmarkContract.Effect>> {
        return when (event) {
            BookmarkContract.Event.Init -> {
                state.copy(currentSort = SortType.TITLE_ASC) to listOf(BookmarkContract.Effect.LoadData)
            }

            is BookmarkContract.Event.OnSearchClicked -> {
                val filtered = state.items
                    .filterByPrice(state.startPrice, state.endPrice)
                    .filterByQuery(event.query)
                state.copy(filteredItems = filtered, query = event.query) to emptyList()
            }


            is BookmarkContract.Event.OnSort -> {
                val sortedList = when (event.type) {
                    SortType.TITLE_ASC -> state.filteredItems.sortedBy { it.title }
                    SortType.TITLE_DESC -> state.filteredItems.sortedByDescending { it.title }
                    else -> state.items
                }
                state.copy(currentSort = event.type, filteredItems = sortedList) to emptyList()
            }

            is BookmarkContract.Event.OnPriceFilter -> {
                val priceFilteredList = state.items.filter {
                    it.price in event.startPrice..event.endPrice
                }

                // 가격 필터 조건 저장
                state.copy(
                    filteredItems = priceFilteredList,
                    startPrice = event.startPrice,
                    endPrice = event.endPrice
                ) to emptyList()
            }

            is BookmarkContract.Event.OnPriceReset -> {
                state.copy(
                    filteredItems = state.items,
                    startPrice = null, endPrice = null
                ) to emptyList()
            }

            is BookmarkContract.Event.OnToggleBookmark -> {
                state to listOf(
                    BookmarkContract.Effect.BookmarkUpdated(
                        event.item,
                        event.isBookmark
                    )
                )
            }

            is BookmarkContract.Event.OnClickItem -> {
                state to listOf(BookmarkContract.Effect.NavigateToDetail(event.item))
            }

            is BookmarkContract.Event.OnSearchSuccess -> {
                state.copy(
                    items = event.items.sortByType(state.currentSort),
                    filteredItems = event.items.sortByType(state.currentSort),
                    isLoading = false
                ) to emptyList()
            }

            is BookmarkContract.Event.OnSearchFailed -> {
                state.copy(
                    isLoading = false,
                    errorMessage = event.message
                ) to listOf(BookmarkContract.Effect.ShowToast(event.message))
            }

            BookmarkContract.Event.Loading -> state.copy(isLoading = true) to emptyList()
        }

    }


    private fun List<BookDetailUiModel>.filterByQuery(query: String): List<BookDetailUiModel> =
        if (query.isBlank()) this
        else filter {
            it.title.contains(query, ignoreCase = true) ||
                    it.author.any { a -> a.contains(query, ignoreCase = true) }
        }

    private fun List<BookDetailUiModel>.filterByPrice(
        startPrice: Int?,
        endPrice: Int?
    ): List<BookDetailUiModel> =
        if (startPrice != null && endPrice != null)
            filter { it.price in startPrice..endPrice }
        else this

    private fun List<BookDetailUiModel>.sortByType(type: SortType): List<BookDetailUiModel> =
        when (type) {
            SortType.TITLE_ASC -> sortedBy { it.title }
            SortType.TITLE_DESC -> sortedByDescending { it.title }
            else -> this
        }
}