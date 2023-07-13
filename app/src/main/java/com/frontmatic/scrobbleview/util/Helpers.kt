package com.frontmatic.scrobbleview.util

import java.text.NumberFormat
import java.util.Locale

fun formatNumberWithSeparator(number: Int): String {
    val numberFormat = NumberFormat.getInstance(Locale.getDefault())
    return numberFormat.format(number)
}