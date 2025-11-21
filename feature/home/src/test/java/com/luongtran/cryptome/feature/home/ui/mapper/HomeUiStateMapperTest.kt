package com.luongtran.cryptome.feature.home.ui.mapper

import com.luongtran.cryptome.core.domain.CurrencyInfo
import com.luongtran.cryptome.core.ui.utils.DefaultNumberFormatter
import com.luongtran.cryptome.core.ui.utils.NumberFormatter
import com.luongtran.cryptome.feature.home.ui.model.CurrencyUi
import com.luongtran.cryptome.feature.home.ui.model.FilterUi
import com.luongtran.cryptome.feature.home.ui.model.HomeUiState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

class HomeUiStateMapperTest {
    private val filterUi = FilterUi.default()
    private val numberFormatter: NumberFormatter = DefaultNumberFormatter(
        priceFormatter = NumberFormat.getCurrencyInstance(Locale.US).apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
        },
        percentFormatter = NumberFormat.getInstance(Locale.US).apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
        },
    )

    @Test
    fun emptyList_mapsToEmptyState() {
        val result = emptyList<CurrencyInfo>().toHomeUiState(filterUi, numberFormatter)
        assertTrue(result is HomeUiState.Empty)
        result as HomeUiState.Empty
        assertEquals(filterUi.selectedOption, result.filterUi.selectedOption)
    }

    @Test
    fun cryptoList_mapsToSuccessWithCryptoUi() {
        val list = listOf(
            CurrencyInfo.Crypto(
                id = "btc",
                name = "Bitcoin",
                symbol = "BTC",
                priceUsd = BigDecimal("1000.12"),
                changePercent24Hr = BigDecimal("2.50"),
                marketCapUsd = BigDecimal("10000"),
                rank = 1,
                tradable = true,
            )
        )
        val result = list.toHomeUiState(filterUi, numberFormatter) as HomeUiState.Success
        assertEquals(1, result.currencies.size)
        val ui = result.currencies.first() as CurrencyUi.Crypto
        assertEquals("btc", ui.id)
        assertEquals("Bitcoin", ui.name)
        assertEquals("BTC", ui.symbol)
        assertEquals("$1,000.12", ui.price.formatted)
        assertEquals("+2.50%", ui.changePercent24Hr.formatted)
        assertTrue(ui.tradable)
    }

    @Test
    fun fiatList_mapsToSuccessWithFiatUi() {
        val list = listOf(
            CurrencyInfo.Fiat(
                id = "usd",
                name = "US Dollar",
                symbol = "$",
                code = "USD",
                priceUsd = BigDecimal("1.00"),
                tradable = true,
            )
        )
        val result = list.toHomeUiState(filterUi, numberFormatter) as HomeUiState.Success
        assertEquals(1, result.currencies.size)
        val ui = result.currencies.first() as CurrencyUi.Fiat
        assertEquals("usd", ui.id)
        assertEquals("US Dollar", ui.name)
        assertEquals("$", ui.symbol)
        assertEquals("USD", ui.code)
        assertEquals("$1.00", ui.exchangeRateToUsd.formatted)
        assertTrue(ui.tradable)
    }

    @Test
    fun mixedList_mapsAllToSuccess() {
        val list = listOf(
            CurrencyInfo.Crypto(
                id = "btc",
                name = "Bitcoin",
                symbol = "BTC",
                priceUsd = BigDecimal("1000"),
                changePercent24Hr = BigDecimal("2.00"),
                marketCapUsd = BigDecimal("10000"),
                rank = 1,
                tradable = true,
            ),
            CurrencyInfo.Fiat(
                id = "usd",
                name = "US Dollar",
                symbol = "$",
                code = "USD",
                priceUsd = BigDecimal("1.00"),
                tradable = false,
            )
        )
        val result = list.toHomeUiState(filterUi, numberFormatter) as HomeUiState.Success
        assertEquals(2, result.currencies.size)
        val btc = result.currencies.first { it.id == "btc" } as CurrencyUi.Crypto
        val usd = result.currencies.first { it.id == "usd" } as CurrencyUi.Fiat
        assertEquals("$1,000.00", btc.price.formatted)
        assertEquals("+2.00%", btc.changePercent24Hr.formatted)
        assertEquals("$1.00", usd.exchangeRateToUsd.formatted)
        assertFalse(usd.tradable)
    }
}