package com.jhs.ui.main.search

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jhs.presentation.model.BookDetailUiModel
import com.jhs.presentation.search.SearchViewModel
import com.jhs.presentation.search.SearchContract
import com.jhs.ui.main.components.layout.LoadingOverlay

@Composable
fun SearchRoute(
    onNavigateDetail: (BookDetailUiModel) -> Unit,
    vm: SearchViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state = vm.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        vm.effect.collect { effect ->
            when (effect) {
                is SearchContract.Effect.NavigateDetail -> {
                    onNavigateDetail(effect.item)
                }

                is SearchContract.Effect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }

                is SearchContract.Effect.BookmarkUpdated -> {
                    vm.toggleBookmark(effect.item, effect.isBookmark)
                }

                is SearchContract.Effect.LoadData -> {
                    vm.fetchBooks(effect.query, effect.sort.value)
                }

            }
        }
    }

    Box(Modifier.fillMaxSize()) {
        SearchBookScreen(
            state = state.value,
            sendEvent = vm::dispatch
        )
        LoadingOverlay(isVisible = state.value.isLoading)
    }
}

