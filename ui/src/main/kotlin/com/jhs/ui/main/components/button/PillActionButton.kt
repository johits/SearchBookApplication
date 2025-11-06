package com.jhs.ui.main.components.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jhs.ui.R

@Composable
fun PillActionButton(modifier: Modifier = Modifier, onClick: () -> Unit, @DrawableRes id: Int, name: String) {
    OutlinedButton(
        onClick = onClick,
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier
            .height(20.dp)
            .defaultMinSize(minWidth = 0.dp, minHeight = 0.dp),
        shape = RoundedCornerShape(18.dp)
    ) {
        Icon(
            painter = painterResource(id),
            contentDescription = null,
            modifier = Modifier.size(15.dp),
            tint = Color.Black
        )
        Spacer(Modifier.size(4.dp))
        Text(
            name,
            color = Color.Black,
            fontSize = 8.sp
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PrePillActionButton() {
    PillActionButton(
        onClick = {},
        id = R.drawable.sort,
        name = "정렬"
    )
}