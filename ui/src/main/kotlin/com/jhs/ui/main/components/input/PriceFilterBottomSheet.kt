package com.jhs.ui.main.components.input

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriceFilterBottomSheet(
    startPrice: Int?,
    endPrice: Int?,
    onReset: () -> Unit,
    onPriceApply: (Int, Int) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val focusManager = LocalFocusManager.current
    var displayStart by rememberSaveable { mutableStateOf("") }
    var displayEnd by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(startPrice, endPrice) {
        if (startPrice != null && endPrice != null) {
            displayStart = formatWithComma(startPrice.toString())
            displayEnd = formatWithComma(endPrice.toString())
        } else {
            displayStart = ""
            displayEnd = ""
        }
    }


    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color.White,
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text("가격", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = displayStart,
                    onValueChange = { newValue ->
                        val digits = newValue.filter { it.isDigit() }
                        displayStart = formatWithComma(digits)
                    },
                    placeholder = { Text("0", color = Color.LightGray) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                    textStyle = LocalTextStyle.current.copy(color = Color(0xFF7E57C2))
                )
                Text("~")
                OutlinedTextField(
                    value = displayEnd,
                    onValueChange = { newValue ->
                        val digits = newValue.filter { it.isDigit() }
                        displayEnd = formatWithComma(digits)
                    },
                    placeholder = { Text("9,999,999", color = Color.LightGray) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                    textStyle = LocalTextStyle.current.copy(color = Color(0xFF7E57C2))
                )
            }

            Spacer(Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        displayStart = ""
                        displayEnd = ""
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF2F2F2),
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("초기화")
                }

                Button(
                    onClick = {
                        val start = displayStart.replace(",", "").toIntOrNull()
                        val end = displayEnd.replace(",", "").toIntOrNull()

                        // 입력 안 했으면 전체 복원
                        if (start == null && end == null) {
                            Toast.makeText(
                                context,
                                "금액이 설정되지 않았습니다. 전체 리스트를 표시합니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                            onReset()
                            onDismiss()
                            return@Button
                        }


                        if (start != null && end != null && start >= end) {
                            Toast.makeText(context, "금액을 올바르게 설정해주세요.", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        val safeStart = start ?: 0
                        val safeEnd = end ?: 1_000_000
                        onPriceApply(safeStart, safeEnd)
                        focusManager.clearFocus()
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF333333),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("적용하기")
                }
            }

            Spacer(Modifier.height(12.dp))
        }
    }
}

private fun formatWithComma(input: String): String {
    return try {
        val number = input.toLongOrNull() ?: return input
        NumberFormat.getNumberInstance(Locale.KOREA).format(number)
    } catch (e: Exception) {
        input
    }
}

@Preview(showBackground = true)
@Composable
fun PrePriceFilterBottomSheet() {
    PriceFilterBottomSheet(
        startPrice = null,
        endPrice = null,
        onReset = {},
        onPriceApply = { _, _ -> },
        onDismiss = {}
    )
}
