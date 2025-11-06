package com.jhs.presentation.detail

import android.util.Log
import javax.inject.Inject

class DetailReducer @Inject constructor() {
    fun reduce(
        state: DetailContract.State,
        event: DetailContract.Event
    ): Pair<DetailContract.State, List<DetailContract.Effect>> {
        return when (event) {
            is DetailContract.Event.Init -> {
                state.copy(item = event.item) to emptyList()
            }

            is DetailContract.Event.OnToggleBookmark -> {
                Log.w("조혜수", "OnToggleBookmark:${event.item}")
                val updatedItem = state.item.copy(isBookmarked = event.isBookmark)
                updatedItem.let {
                    state.copy(item = it) to listOf(
                        DetailContract.Effect.BookmarkUpdated(
                            it,
                            event.isBookmark
                        )
                    )
                }
            }

            DetailContract.Event.OnBack -> state to listOf(DetailContract.Effect.NavigateBack)
        }
    }
}