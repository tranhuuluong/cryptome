package com.luongtran.cryptome.core.ui.mapper

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.math.BigDecimal

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class PriceMapperTest {
    @Test
    fun `toDisplayableNumber formats USD with 2 decimals`() {
        val value = BigDecimal("1234.567")
        val displayable = value.toDisplayableNumber()
        assertEquals(value, displayable.value)
        assertEquals("$1,234.57", displayable.formatted)
    }

    @Test
    fun `toPercentage formats negative percent with sign and 2 decimals`() {
        val value = BigDecimal("-12.3456")
        val displayable = value.toPercentage()
        assertEquals(value, displayable.value)
        assertEquals("-12.35%", displayable.formatted)
    }

    @Test
    fun `toPercentage formats positive percent with plus sign`() {
        val value = BigDecimal("5.1")
        val displayable = value.toPercentage()
        assertEquals(value, displayable.value)
        assertEquals("+5.10%", displayable.formatted)
    }
}