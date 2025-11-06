package com.jhs.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhs.domain.model.Document
import com.jhs.domain.usecase.UpdateBookmarkUseCase
import com.jhs.presentation.model.BookDetailUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val updateBookmarkUseCase: UpdateBookmarkUseCase,
    private val reducer: DetailReducer
) : ViewModel() {
    private val _uiState = MutableStateFlow(DetailContract.State())
    val uiState = _uiState.asStateFlow()

    private val _effect = Channel<DetailContract.Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun dispatch(event: DetailContract.Event) {
        val (newState, effects) = reducer.reduce(_uiState.value, event)
        _uiState.value = newState
        effects.forEach { effect ->
            viewModelScope.launch { _effect.send(effect) }
        }
    }

    fun toggleBookmark(
        item: BookDetailUiModel,
        isBookmark: Boolean
    ) {
        viewModelScope.launch {
            val domain = Document(
                title = item.title,
                contents = item.description,
                thumbnail = item.thumbnail.orEmpty(),
                authors = item.author,
                publisher = item.publisher,
                datetime = item.pubDate,
                isbn = item.isbn,
                price = item.price,
                salePrice = item.salePrice
            )

            // 실제 저장
            updateBookmarkUseCase(domain, isBookmark)

            // 필요하면 토스트 이펙트도 가능
            // _effect.send(DetailContract.Effect.ShowToast(if (isBookmark) "북마크 추가" else "북마크 해제"))
        }
    }
}