package com.jhs.presentation.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhs.data_resource.DataResource
import com.jhs.domain.usecase.GetBookmarksUseCase
import com.jhs.domain.usecase.UpdateBookmarkUseCase
import com.jhs.presentation.model.BookDetailUiModel
import com.jhs.presentation.model.toDomain
import com.jhs.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val getBookmarksUseCase: GetBookmarksUseCase,
    private val updateBookmarkUseCase: UpdateBookmarkUseCase,
    private val reducer: BookmarkReducer
) : ViewModel() {
    private val _uiState = MutableStateFlow(BookmarkContract.State())
    val uiState = _uiState.asStateFlow()

    private val _effect = Channel<BookmarkContract.Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    private var fetchJob: Job? = null

    fun dispatch(event: BookmarkContract.Event) {
        val (newState, effects) = reducer.reduce(_uiState.value, event)
        _uiState.value = newState
        effects.forEach { effect ->
            viewModelScope.launch { _effect.send(effect) }
        }
    }

    fun fetchBooks() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            getBookmarksUseCase()
                .map { res ->
                    when (res) {
                        is DataResource.Success -> {
                            res.data.map { it.toPresentation().copy(isBookmarked = true) }
                        }

                        is DataResource.Loading -> {
                            dispatch(BookmarkContract.Event.Loading)
                            emptyList()

                        }

                        is DataResource.Error -> {
                            dispatch(
                                BookmarkContract.Event.OnSearchFailed(
                                    res.throwable.message ?: "데이터를 불러오지 못했습니다."
                                )
                            )
                            emptyList()
                        }
                    }
                }.catch { e ->
                    dispatch(
                        BookmarkContract.Event.OnSearchFailed(
                            e.message ?: "예기치 못한 오류가 발생했습니다."
                        )
                    )
                }
                .collect { uiItems ->
                    dispatch(BookmarkContract.Event.OnSearchSuccess(uiItems))

                }
        }
    }

    fun toggleBookmark(item: BookDetailUiModel, isBookmark: Boolean) {
        viewModelScope.launch {
            updateBookmarkUseCase(item.toDomain(), isBookmark)
        }
    }

}