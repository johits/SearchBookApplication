package com.jhs.ui.main.components.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.jhs.presentation.model.SortType
import com.jhs.ui.R
import com.jhs.ui.main.components.button.PillActionButton
import com.jhs.ui.main.navigation.Destination

@Composable
fun SortDropdownMenu(
    currentTab: Destination,
    selectedSort: String,
    onSortSelected: (SortType) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val sortOptions = remember(currentTab) {
        when (currentTab) {
            Destination.Search -> listOf(SortType.ACCURACY, SortType.RECENCY)
            Destination.Favorites -> listOf(SortType.TITLE_ASC, SortType.TITLE_DESC)
            else -> emptyList()
        }
    }


    Box {
        PillActionButton(
            onClick = { expanded = true },
            id = R.drawable.sort,
            name = "정렬"
        )

        // 말풍선 메뉴
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            sortOptions.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.displayName) },
                    onClick = {
                        onSortSelected(option)
                        expanded = false
                    },
                    trailingIcon = {
                        if (selectedSort == option.displayName) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null
                            )
                        }
                    }
                )
            }
        }
    }
}


@Preview(
    showBackground = true,
    widthDp = 300,
    heightDp = 200
)
@Composable
fun PreviewSortDropdownMenu() {
    SortDropdownMenu(
        currentTab = Destination.Search,
        selectedSort = "발행순",
        onSortSelected = {}
    )
}
