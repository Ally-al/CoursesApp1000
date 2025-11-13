package com.example.core.utils

import java.text.SimpleDateFormat
import java.util.*

fun formatDate(input: String): String {
    return try {
        val parser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = parser.parse(input)
        val formatter = SimpleDateFormat("dd MMMM yyyy", Locale.forLanguageTag("ru-RU"))
        formatter.format(date!!)
    } catch (e: Exception) {
        input
    }
}

fun parseIsoDateOrNull(input: String?): Date? {
    if (input.isNullOrBlank()) return null
    return try {
        val parser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        parser.parse(input.trim())
    } catch (e: Exception) {
        null
    }
}
