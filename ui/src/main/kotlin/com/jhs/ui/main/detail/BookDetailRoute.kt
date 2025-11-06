package com.jhs.ui.main.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jhs.presentation.detail.DetailContract
import com.jhs.presentation.detail.DetailViewModel
import com.jhs.presentation.model.BookDetailUiModel

@Composable
fun BookDetailRoute(
    item: BookDetailUiModel,
    onBack: () -> Unit,
    vm: DetailViewModel = hiltViewModel(),
) {
    val state = vm.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(item.isbn) {
        vm.dispatch(DetailContract.Event.Init(item))
    }

    LaunchedEffect(Unit) {
        vm.effect.collect { effect ->
            when (effect) {
                DetailContract.Effect.NavigateBack -> onBack()
                is DetailContract.Effect.BookmarkUpdated -> {
                    vm.toggleBookmark(effect.item, effect.isBookmark)
                }

                is DetailContract.Effect.ShowToast -> {
                }
            }
        }
    }

    BookDetailScreen(
        state = state.value,
        sendEvent = vm::dispatch
    )
}
