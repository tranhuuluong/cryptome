package com.luongtran.cryptome.feature.coindetail.ui.mapper

import com.luongtran.cryptome.core.common.model.DataStateError
import com.luongtran.cryptome.core.common.model.DataStateSuccess
import com.luongtran.cryptome.core.common.model.StateLoading
import com.luongtran.cryptome.core.domain.CoinDetail
import com.luongtran.cryptome.core.ui.utils.DefaultNumberFormatter
import com.luongtran.cryptome.core.ui.utils.NumberFormatter
import com.luongtran.cryptome.feature.coindetail.ui.model.CoinDetailUiState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.text.NumberFormat
import java.util.Locale

class CoinDetailUiMapperTest {
    private val formatter: NumberFormatter = DefaultNumberFormatter(
        priceFormatter = NumberFormat.getCurrencyInstance(Locale.US).apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
        },
        percentFormatter = NumberFormat.getInstance(Locale.US).apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
        },
        cryptoAmountFormatter = NumberFormat.getNumberInstance(),
    )

    @Test
    fun success_mapsAllFields() {
        val detail = CoinDetail(
            id = "btc",
            symbol = "BTC",
            name = "Bitcoin",
            slug = "bitcoin",
            priceHistory = listOf(1.0, 2.0, 3.0),
            rank = 1,
            priceUsd = 1000.12,
            marketCapUsd = 500_000_000.0,
            volume24Hr = 123_456.0,
            priceChange24h = 2.5,
            allTimeHigh = 69_000.0,
            allTimeLow = 65.0,
            totalSupply = 19_000_000.0,
            maxSupply = 21_000_000.0,
            circulatingSupply = 18_500_000.0,
        )

        val uiState = DataStateSuccess(detail)
            .toCoinDetailUiState(formatter) as CoinDetailUiState.Success
        assertEquals("btc", uiState.id)
        assertEquals("Bitcoin", uiState.name)
        assertEquals("BTC", uiState.symbol)
        assertEquals("$1,000.12", uiState.priceUsd.formatted)
        assertEquals("$500.00M", uiState.marketCapUsd.formatted)
        assertEquals(1, uiState.rank)
        assertEquals("$123,456.00", uiState.volume24Hr.formatted)
        assertEquals("+2.50%", uiState.priceChange24h.formatted)
        assertEquals("$69,000.00", uiState.allTimeHigh.formatted)
        assertEquals("$65.00", uiState.allTimeLow.formatted)
        assertEquals("19M BTC", uiState.totalSupply.formatted)
        assertEquals("21M BTC", uiState.maxSupply.formatted)
        assertEquals("18.5M BTC", uiState.circulatingSupply.formatted)
    }

    @Test
    fun success_withNaN_formatsAsNA() {
        val nan = Double.NaN
        val detail = CoinDetail(
            id = "x",
            symbol = "X",
            name = "Test",
            slug = "test",
            priceHistory = emptyList(),
            rank = Int.MIN_VALUE,
            priceUsd = nan,
            marketCapUsd = nan,
            volume24Hr = nan,
            priceChange24h = nan,
            allTimeHigh = nan,
            allTimeLow = nan,
            totalSupply = nan,
            maxSupply = nan,
            circulatingSupply = nan,
        )

        val uiState = DataStateSuccess(detail)
            .toCoinDetailUiState(formatter) as CoinDetailUiState.Success

        assertEquals("N/A", uiState.priceUsd.formatted)
        assertEquals("N/A", uiState.marketCapUsd.formatted)
        assertEquals("N/A", uiState.volume24Hr.formatted)
        assertEquals("N/A", uiState.priceChange24h.formatted)
        assertEquals("N/A", uiState.allTimeHigh.formatted)
        assertEquals("N/A", uiState.allTimeLow.formatted)
        assertEquals("N/A", uiState.totalSupply.formatted)
        assertEquals("N/A", uiState.maxSupply.formatted)
        assertEquals("N/A", uiState.circulatingSupply.formatted)
    }

    @Test
    fun error_mapsToErrorState() {
        val uiState = DataStateError(Exception("fail")).toCoinDetailUiState(formatter)
        assertEquals("fail", (uiState as CoinDetailUiState.Error).message)
    }

    @Test
    fun loading_mapsToLoadingState() {
        val uiState = StateLoading.toCoinDetailUiState(formatter)
        assertTrue(uiState is CoinDetailUiState.Loading)
    }
}