package com.jhs.ui.main.components.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun BookmarkToggleButton(
    bookmarkState: Boolean,
    onClickBookmark: (Boolean) -> Unit
) {
//    var checkState by rememberSaveable(bookmarkState) { mutableStateOf(bookmarkState) }
    IconToggleButton(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.End),
        checked = bookmarkState,
        onCheckedChange = { newChecked->
            onClickBookmark(newChecked)
        }
    ) {
        Icon(
            imageVector =
                if (bookmarkState) {
                    Icons.Default.Favorite
                } else {
                    Icons.Default.FavoriteBorder
                },
            contentDescription = null
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreBookmarkToggleButton() {
//    var checkState = remember { mutableStateOf(false) }
    BookmarkToggleButton(
        bookmarkState = true,
        onClickBookmark = {}
    )
}