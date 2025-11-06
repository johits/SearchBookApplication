package com.jhs.ui.main.components.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle

@Composable
internal fun InfoText(label: String, value: String, boldValue: Boolean = false) {
    val text = buildAnnotatedString {
        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("$label : ") }
        if (boldValue) withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append(value) } else append(
            value
        )
    }
    Text(text, maxLines = 1, overflow = TextOverflow.Ellipsis)
}