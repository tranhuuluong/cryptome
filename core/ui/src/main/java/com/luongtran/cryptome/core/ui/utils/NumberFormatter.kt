package com.luongtran.cryptome.core.ui.utils

import java.text.NumberFormat

interface NumberFormatter {
    fun formatPrice(price: Number): String

    fun formatPercent(percent: Number): String
}

class DefaultNumberFormatter(
    private val priceFormatter: NumberFormat,
    private val percentFormatter: NumberFormat,
) : NumberFormatter {
    override fun formatPrice(price: Number): String {
        return priceFormatter.format(price)
    }

    override fun formatPercent(percent: Number): String {
        val formattedValue = percentFormatter.format(percent)
        val value = percent.toDouble()
        return when {
            value > 0 -> "+$formattedValue%"
            else -> "$formattedValue%"
        }
    }
}