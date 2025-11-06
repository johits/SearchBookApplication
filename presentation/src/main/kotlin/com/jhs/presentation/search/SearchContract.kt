package com.jhs.presentation.search


import com.jhs.presentation.model.BookDetailUiModel
import com.jhs.presentation.model.SortType


object SearchContract {

    data class State(
        val query: String = "",
        val items: List<BookDetailUiModel> = emptyList(),
        val currentSort: SortType = SortType.ACCURACY,
        val errorMessage: String? = null,
        val isLoading: Boolean = false
    )

    sealed interface Event {
        data class OnSearch(val query: String, val sort: SortType) : Event
        data class OnClickItem(val item: BookDetailUiModel) : Event
        data class OnToggleBookmark(val item: BookDetailUiModel, val isBookmark: Boolean) : Event

        data object Loading : Event
        data class OnSearchSuccess(val items: List<BookDetailUiModel>) : Event
        data class OnSearchFailed(val message: String) : Event
    }

    sealed interface Effect {
        data class LoadData(val query: String, val sort: SortType) : Effect
        data class ShowToast(val message: String) : Effect
        data class NavigateDetail(val item: BookDetailUiModel) : Effect
        data class BookmarkUpdated(val item: BookDetailUiModel, val isBookmark: Boolean) : Effect
    }
}
