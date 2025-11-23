package com.luongtran.cryptome.core.ui.utils

import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.NumberFormat
import java.util.Locale

class DefaultNumberFormatterTest {
    private val priceFormatter: NumberFormat = NumberFormat.getCurrencyInstance(Locale.US).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }

    private val percentFormatter: NumberFormat = NumberFormat.getInstance(Locale.US).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }

    private val cryptoAmountFormatter: NumberFormat = NumberFormat.getNumberInstance()

    private val formatter = DefaultNumberFormatter(
        priceFormatter = priceFormatter,
        percentFormatter = percentFormatter,
        cryptoAmountFormatter = cryptoAmountFormatter,
    )

    @Test
    fun formatPrice_formatsWithFractionDigits() {
        assertEquals("$1,234.50", formatter.formatPrice(1234.5))
    }

    @Test
    fun formatPrice_usesMillionSuffix() {
        assertEquals("$1.50M", formatter.formatPrice(1_500_000.0))
    }

    @Test
    fun formatPrice_usesBillionSuffix() {
        assertEquals("$2.30B", formatter.formatPrice(2_300_000_000.0))
    }

    @Test
    fun formatPrice_handlesNaN() {
        assertEquals("N/A", formatter.formatPrice(Double.NaN))
    }

    @Test
    fun formatPercent_positiveAddsPlus() {
        assertEquals("+1.23%", formatter.formatPercent(1.23))
    }

    @Test
    fun formatPercent_zeroNoPlus() {
        assertEquals("0.00%", formatter.formatPercent(0.0))
    }

    @Test
    fun formatPercent_negativeNoPlus() {
        assertEquals("-5.50%", formatter.formatPercent(-5.5))
    }

    @Test
    fun formatPercent_handlesNaN() {
        assertEquals("N/A", formatter.formatPercent(Double.NaN))
    }

    @Test
    fun formatCryptoAmount_plainValue() {
        assertEquals("12,345.678 BTC", formatter.formatCryptoAmount(12345.678, "BTC"))
    }

    @Test
    fun formatCryptoAmount_usesMillionSuffix() {
        assertEquals("1.5M BTC", formatter.formatCryptoAmount(1_500_000.0, "BTC"))
    }

    @Test
    fun formatCryptoAmount_usesBillionSuffix() {
        assertEquals("2.3B BTC", formatter.formatCryptoAmount(2_300_000_000.0, "BTC"))
    }

    @Test
    fun formatCryptoAmount_handlesNaN() {
        assertEquals("N/A", formatter.formatCryptoAmount(Double.NaN, "BTC"))
    }
}