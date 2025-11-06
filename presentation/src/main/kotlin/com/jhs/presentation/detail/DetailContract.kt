package com.jhs.presentation.detail

import com.jhs.presentation.model.BookDetailUiModel


object DetailContract {

    data class State(
        val item: BookDetailUiModel = BookDetailUiModel.EMPTY,
        val isLoading: Boolean = false,
        val errorMessage: String? = null
    )

    sealed interface Event {
        data class Init(val item: BookDetailUiModel) : Event
        data class OnToggleBookmark(val item: BookDetailUiModel, val isBookmark: Boolean) : Event
        data object OnBack : Event
    }

    sealed interface Effect {
        data class BookmarkUpdated(val item: BookDetailUiModel, val isBookmark: Boolean) : Effect
        data object NavigateBack : Effect
        data class ShowToast(val message: String) : Effect
    }
}