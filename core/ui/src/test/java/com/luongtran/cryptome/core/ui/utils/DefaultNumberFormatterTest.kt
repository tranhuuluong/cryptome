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

    private val formatter = DefaultNumberFormatter(
        priceFormatter = priceFormatter,
        percentFormatter = percentFormatter,
    )

    @Test
    fun `formatPrice formats with 2 fraction digits`() {
        val result = formatter.formatPrice(1234.5)

        assertEquals("$1,234.50", result)
    }

    @Test
    fun `formatPercent adds plus sign for positive values`() {
        val result = formatter.formatPercent(1.23)

        assertEquals("+1.23%", result)
    }

    @Test
    fun `formatPercent no plus sign for zero`() {
        val result = formatter.formatPercent(0)

        assertEquals("0.00%", result)
    }

    @Test
    fun `formatPercent no plus sign for negative values`() {
        val result = formatter.formatPercent(-5.5)

        assertEquals("-5.50%", result)
    }
}