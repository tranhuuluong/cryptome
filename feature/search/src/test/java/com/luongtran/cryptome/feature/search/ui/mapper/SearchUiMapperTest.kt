package com.luongtran.cryptome.feature.search.ui.mapper

import com.luongtran.cryptome.core.domain.CurrencyInfo
import com.luongtran.cryptome.core.ui.utils.DefaultNumberFormatter
import com.luongtran.cryptome.core.ui.utils.NumberFormatter
import com.luongtran.cryptome.feature.search.ui.model.SearchUiState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

class SearchUiMapperTest {
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
        val result = emptyList<CurrencyInfo>()
            .toSearchUiState(
                query = "btc",
                numberFormatter = numberFormatter
            ) as SearchUiState.Empty
        assertEquals("btc", result.searchQuery)
    }

    @Test
    fun cryptoList_mapsToSuccessWithCryptoUi() {
        val crypto = CurrencyInfo.Crypto(
            id = "btc",
            name = "Bitcoin",
            symbol = "BTC",
            priceUsd = BigDecimal("1000.12"),
            changePercent24Hr = BigDecimal("2.50"),
            marketCapUsd = BigDecimal("10000"),
            rank = 1,
            tradable = true,
        )
        val result = listOf(crypto).toSearchUiState(
            query = "",
            numberFormatter = numberFormatter
        ) as SearchUiState.Success
        val ui = result.currencies.first()
        assertEquals("$1,000.12", ui.priceUsd.formatted)
    }

    @Test
    fun fiatList_mapsToSuccessWithFiatUi() {
        val fiat = CurrencyInfo.Fiat(
            id = "usd",
            name = "US Dollar",
            symbol = "$",
            code = "USD",
            priceUsd = BigDecimal("1.00"),
            tradable = true,
        )
        val result = listOf(fiat).toSearchUiState(
            query = "",
            numberFormatter = numberFormatter
        ) as SearchUiState.Success
        val ui = result.currencies.first()
        assertEquals("$1.00", ui.priceUsd.formatted)
    }

    @Test
    fun cryptoList_mapsToPopularSearches() {
        val crypto = CurrencyInfo.Crypto(
            id = "eth",
            name = "Ethereum",
            symbol = "ETH",
            priceUsd = BigDecimal("2000.00"),
            changePercent24Hr = BigDecimal("1.25"),
            marketCapUsd = BigDecimal("20000"),
            rank = 2,
            tradable = true,
        )
        val result = listOf(crypto).toPopularSearches(numberFormatter)
        val ui = result.first()
        assertEquals("$2,000.00", ui.priceUsd.formatted)
        assertEquals("+1.25%", ui.changePercent24Hr.formatted)
    }

    @Test
    fun fiatList_doesNotMapToPopularSearches() {
        val fiat = CurrencyInfo.Fiat(
            id = "eur",
            name = "Euro",
            symbol = "€",
            code = "EUR",
            priceUsd = BigDecimal("1.10"),
            tradable = true,
        )
        val result = listOf(fiat).toPopularSearches(numberFormatter)
        assertTrue(result.isEmpty())
    }

}