package com.jhs.ui.main.components.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jhs.presentation.model.SortType
import com.jhs.ui.main.navigation.Destination

@Composable
fun SortAndFilterRow(
    type: Destination,
    selectedSort: SortType,
    onSortSelected: (SortType) -> Unit,
    filterSlot: (@Composable RowScope.() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(bottom = 10.dp, start = 20.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = selectedSort.displayName,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.weight(1f))

        // 필터 관련 UI 삽입 슬롯 (Bookmark 전용)
        filterSlot?.invoke(this)

        Spacer(Modifier.size(3.dp))

        SortDropdownMenu(
            currentTab = type,
            selectedSort = selectedSort.displayName,
            onSortSelected = onSortSelected
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreSortAndFilterRow() {
    SortAndFilterRow(
        type = Destination.Search,
        selectedSort = SortType.ACCURACY,
        onSortSelected = {}
    )
}
