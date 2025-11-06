package com.jhs.presentation.bookmark

import com.jhs.presentation.model.BookDetailUiModel
import com.jhs.presentation.model.SortType

object BookmarkContract {
    data class State(
        val filteredItems: List<BookDetailUiModel> = emptyList(),
        val items: List<BookDetailUiModel> = emptyList(),
        val currentSort: SortType = SortType.TITLE_ASC,
        val startPrice: Int? = null,
        val endPrice: Int? = null,
        val query: String = "",
        val isLoading: Boolean = false,
        val errorMessage: String? = null
    )

    sealed interface Event {
        data object Init : Event
        data class OnSearchClicked(val query: String) : Event
        data class OnSort(val type: SortType) : Event
        data class OnPriceFilter(val startPrice: Int, val endPrice: Int) : Event
        data class OnPriceReset(val startPrice: Int, val endPrice: Int) : Event
        data class OnClickItem(val item: BookDetailUiModel) : Event
        data class OnToggleBookmark(val item: BookDetailUiModel, val isBookmark: Boolean) : Event
        data class OnSearchFailed(val message: String) : Event
        data class OnSearchSuccess(val items: List<BookDetailUiModel>) : Event
        data object Loading : Event
    }

    sealed interface Effect {
        data object LoadData : Effect
        data class BookmarkUpdated(val item: BookDetailUiModel, val isBookmark: Boolean) : Effect
        data class NavigateToDetail(val item: BookDetailUiModel) : Effect
        data class ShowToast(val message: String) : Effect
    }
}