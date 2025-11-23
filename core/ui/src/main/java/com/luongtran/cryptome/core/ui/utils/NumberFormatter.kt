package com.luongtran.cryptome.core.ui.utils

import java.text.NumberFormat

interface NumberFormatter {
    fun formatPrice(value: Double): String
    fun formatPercent(value: Double): String
    fun formatCryptoAmount(value: Double, symbol: String): String
}

class DefaultNumberFormatter(
    private val priceFormatter: NumberFormat,
    private val percentFormatter: NumberFormat,
    private val cryptoAmountFormatter: NumberFormat,
) : NumberFormatter {
    override fun formatPrice(value: Double): String {
        return when {
            value.isNaN() -> "N/A"
            value >= 1_000_000_000 -> "${priceFormatter.format(value / 1_000_000_000)}B"
            value >= 1_000_000 -> "${priceFormatter.format(value / 1_000_000)}M"
            else -> priceFormatter.format(value)
        }
    }

    override fun formatPercent(value: Double): String {
        val formatted = percentFormatter.format(value)
        return when {
            value.isNaN() -> "N/A"
            value > 0 -> "+$formatted%"
            else -> "$formatted%"
        }
    }

    override fun formatCryptoAmount(value: Double, symbol: String): String {
        return when {
            value.isNaN() -> "N/A"
            value >= 1_000_000_000 -> "${cryptoAmountFormatter.format(value / 1_000_000_000)}B $symbol"
            value >= 1_000_000 -> "${cryptoAmountFormatter.format(value / 1_000_000)}M $symbol"
            else -> "${cryptoAmountFormatter.format(value)} $symbol"
        }
    }
}