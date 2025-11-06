package com.jhs.presentation.search

import javax.inject.Inject

class SearchReducer @Inject constructor() {

    fun reduce(
        state: SearchContract.State,
        event: SearchContract.Event
    ): Pair<SearchContract.State, List<SearchContract.Effect>> {

        return when (event) {
            is SearchContract.Event.OnSearch -> {
                state.copy(
                    query = event.query,
                    items = emptyList(),
                    currentSort = event.sort,
                    isLoading = true,
                    errorMessage = null
                ) to listOf(SearchContract.Effect.LoadData(event.query, event.sort))
            }

            SearchContract.Event.Loading -> state.copy(isLoading = true) to emptyList()

            is SearchContract.Event.OnSearchSuccess ->
                state.copy(
                    items = event.items,
                    isLoading = false
                ) to emptyList()

            is SearchContract.Event.OnSearchFailed ->
                state.copy(
                    isLoading = false,
                    errorMessage = event.message
                ) to listOf(
                    SearchContract.Effect.ShowToast(
                        event.message
                    )
                )

            is SearchContract.Event.OnClickItem -> {
                state to listOf(
                    SearchContract.Effect.NavigateDetail(event.item)
                )
            }

            is SearchContract.Event.OnToggleBookmark -> {
                state to listOf(SearchContract.Effect.BookmarkUpdated(event.item, event.isBookmark))
            }

        }
    }
}
