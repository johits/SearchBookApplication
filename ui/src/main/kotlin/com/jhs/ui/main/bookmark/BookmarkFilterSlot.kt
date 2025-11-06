package com.jhs.ui.main.bookmark

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.jhs.presentation.bookmark.BookmarkContract
import com.jhs.ui.R
import com.jhs.ui.main.components.button.PillActionButton
import com.jhs.ui.main.components.input.PriceFilterBottomSheet

@Composable
fun BookmarkFilterSlot(
    showFilter: Boolean,
    onShowFilterChange: (Boolean) -> Unit,
    startPrice: Int?,
    endPrice: Int?,
    sendEvent: (BookmarkContract.Event) -> Unit
) {
    if (showFilter) {
        PriceFilterBottomSheet(
            startPrice = startPrice,
            endPrice = endPrice,
            onReset = {
                sendEvent(BookmarkContract.Event.OnPriceReset(0, 9999999))
            },
            onPriceApply = { start, end ->
                onShowFilterChange(false)
                sendEvent(BookmarkContract.Event.OnPriceFilter(start, end))
            },
            onDismiss = { onShowFilterChange(false) }
        )
    }

    PillActionButton(
        onClick = { onShowFilterChange(true) },
        id = R.drawable.filter,
        name = "필터"
    )
}

@Preview(showBackground = true)
@Composable
private fun PreBookmarkFilterSlot() {
    BookmarkFilterSlot(
        showFilter = true,
        onShowFilterChange = { },
        startPrice = 0,
        endPrice = 10000,
        sendEvent = {}
    )
}