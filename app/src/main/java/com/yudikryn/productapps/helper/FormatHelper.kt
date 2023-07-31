package com.yudikryn.productapps.helper

import java.text.NumberFormat
import java.util.Locale

object FormatHelper {
    fun formatDollar(amount: Double): String {
        val numberFormat = NumberFormat.getCurrencyInstance(Locale.US)
        return numberFormat.format(amount)
    }
}