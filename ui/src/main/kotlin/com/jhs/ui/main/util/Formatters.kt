package com.jhs.ui.main.util

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun Int.toWon(locale: Locale = Locale.KOREA): String =
    "${NumberFormat.getNumberInstance(locale).format(this)}원"

fun String.toKoreanDate(): String {
    return try {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
        parser.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        val date = parser.parse(this)

        val formatter = SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA)
        formatter.format(date ?: Date())
    } catch (e: Exception) {
        this
    }
}