package com.jhs.ui.main.bookmark

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jhs.presentation.bookmark.BookmarkContract
import com.jhs.presentation.bookmark.BookmarkViewModel
import com.jhs.presentation.model.BookDetailUiModel
import com.jhs.ui.main.components.layout.LoadingOverlay

@Composable
fun BookmarkRoute(
    onNavigateDetail: (BookDetailUiModel) -> Unit,
    vm: BookmarkViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state = vm.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        vm.dispatch(BookmarkContract.Event.Init)
    }
    LaunchedEffect(Unit) {
        vm.effect.collect { effect ->
            when (effect) {
                is BookmarkContract.Effect.LoadData -> {
                    vm.fetchBooks()
                }

                is BookmarkContract.Effect.BookmarkUpdated -> {
                    vm.toggleBookmark(effect.item, effect.isBookmark)
                }

                is BookmarkContract.Effect.NavigateToDetail -> {
                    onNavigateDetail(effect.item)
                }

                is BookmarkContract.Effect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        BookmarkScreen(
            state = state.value,
            sendEvent = vm::dispatch
        )
        LoadingOverlay(isVisible = state.value.isLoading)
    }
}