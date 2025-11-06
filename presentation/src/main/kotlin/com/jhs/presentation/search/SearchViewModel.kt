package com.jhs.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhs.data_resource.DataResource
import com.jhs.domain.usecase.GetBookmarksUseCase
import com.jhs.domain.usecase.GetDocumentsUseCase
import com.jhs.domain.usecase.UpdateBookmarkUseCase
import com.jhs.presentation.model.BookDetailUiModel
import com.jhs.presentation.model.toDomain
import com.jhs.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getDocumentsUseCase: GetDocumentsUseCase,
    private val getBookmarksUseCase: GetBookmarksUseCase,
    private val updateBookmarkUseCase: UpdateBookmarkUseCase,
    private val reducer: SearchReducer
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchContract.State())
    val uiState: StateFlow<SearchContract.State> = _uiState

    private val _effect = Channel<SearchContract.Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    private var fetchJob: Job? = null


    fun dispatch(event: SearchContract.Event) {
        val (newState, effects) = reducer.reduce(_uiState.value, event)
        _uiState.value = newState
        effects.forEach { effect ->
            viewModelScope.launch { _effect.send(effect) }
        }
    }


    fun fetchBooks(query: String, sort: String) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            val searchResultFlow = getDocumentsUseCase(query, sort)
            val bookmarksFlow = getBookmarksUseCase()

            combine(searchResultFlow, bookmarksFlow) { remote, bookmarksRes ->
                val bookmarked = when (bookmarksRes) {
                    is DataResource.Success -> {
                        bookmarksRes.data.map { it.isbn }.toSet()
                    }

                    DataResource.Loading -> {
                        dispatch(SearchContract.Event.Loading)
                        emptySet()
                    }

                    is DataResource.Error -> {
                        dispatch(
                            SearchContract.Event.OnSearchFailed(
                                bookmarksRes.throwable.message ?: "데이터를 불러오지 못했습니다."
                            )
                        )
                        emptySet()
                    }
                }

                remote.map { doc ->
                    val isBookmarked = bookmarked.contains(doc.isbn)
                    doc.toPresentation().copy(isBookmarked = isBookmarked)
                }
            }
                .catch { e ->
                    dispatch(
                        SearchContract.Event.OnSearchFailed(
                            e.message ?: "데이터를 불러오지 못했습니다."
                        )
                    )
                }
                .collect { uiItems ->
                    dispatch(SearchContract.Event.OnSearchSuccess(uiItems))
                }
        }
    }


    fun toggleBookmark(
        item: BookDetailUiModel,
        isBookmark: Boolean
    ) {
        viewModelScope.launch {
            updateBookmarkUseCase(item.toDomain(), isBookmark)
        }
    }
}
