package com.luongtran.cryptome.feature.home.ui

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.luongtran.cryptome.core.domain.CurrencyInfo
import com.luongtran.cryptome.core.domain.CurrencyType
import com.luongtran.cryptome.core.ui.utils.DefaultNumberFormatter
import com.luongtran.cryptome.core.ui.utils.NumberFormatter
import com.luongtran.cryptome.feature.home.domain.CurrencyRepository
import com.luongtran.cryptome.feature.home.ui.model.FilterOption
import com.luongtran.cryptome.feature.home.ui.model.HomeUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

class HomeViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var repository: FakeCurrencyRepository
    private lateinit var viewModel: HomeViewModel

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

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = FakeCurrencyRepository()
        viewModel = HomeViewModel(SavedStateHandle(), repository, numberFormatter)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun initialState_emitsLoading_thenEmpty() = runTest {
        viewModel.uiState.test {
            assertTrue(awaitItem() is HomeUiState.Loading)
            val empty = awaitItem()
            empty as HomeUiState.Empty
            assertEquals(FilterOption.Crypto, empty.filterUi.selectedOption)
            assertEquals(false, empty.filterUi.showTradable)
            cancel()
        }
    }

    @Test
    fun insertData_emitsSuccess_withCrypto() = runTest {
        viewModel.uiState.test {
            awaitItem() // Loading
            awaitItem() // Empty
            repository.insertData()
            val success = awaitItem()
            success as HomeUiState.Success
            assertEquals(2, success.currencies.size)
            assertEquals("bitcoin", success.currencies.first().id)
            cancel()
        }
    }

    @Test
    fun changeFilterOption_toFiat_emitsFiatSuccess() = runTest {
        repository.insertData()
        viewModel.uiState.test {
            awaitItem() // Loading
            viewModel.onFilterOptionClick(FilterOption.Fiat)
            val fiatSuccess = awaitItem()
            fiatSuccess as HomeUiState.Success
            assertEquals(FilterOption.Fiat, fiatSuccess.filterUi.selectedOption)
            assertEquals(2, fiatSuccess.currencies.size)
            assertEquals("usd", fiatSuccess.currencies.first().id)
            cancel()
        }
    }

    @Test
    fun toggleTradable_filtersOutNonTradable() = runTest {
        repository.insertData()
        viewModel.uiState.test {
            awaitItem() // Loading
            val success = awaitItem()
            success as HomeUiState.Success
            assertEquals(2, success.currencies.size)

            viewModel.onTradableCheckedChange()
            val successFiltered = awaitItem()
            successFiltered as HomeUiState.Success
            assertEquals(true, successFiltered.filterUi.showTradable)
            assertEquals(1, successFiltered.currencies.size)
            assertEquals("bitcoin", successFiltered.currencies.first().id)
            cancel()
        }
    }

    private class FakeCurrencyRepository : CurrencyRepository {
        private val cryptoFlow = MutableStateFlow<List<CurrencyInfo.Crypto>>(emptyList())
        private val fiatFlow = MutableStateFlow<List<CurrencyInfo.Fiat>>(emptyList())

        private val cryptos = listOf(
            CurrencyInfo.Crypto(
                id = "bitcoin",
                name = "Bitcoin",
                symbol = "BTC",
                priceUsd = BigDecimal(1000),
                changePercent24Hr = BigDecimal(2.0),
                marketCapUsd = BigDecimal(1000000),
                rank = 1,
                tradable = true,
            ),
            CurrencyInfo.Crypto(
                id = "dogecoin",
                name = "Dogecoin",
                symbol = "DOGE",
                priceUsd = BigDecimal(0.1),
                changePercent24Hr = BigDecimal(-1.0),
                marketCapUsd = BigDecimal(1000),
                rank = 2,
                tradable = false,
            )
        )

        private val fiats = listOf(
            CurrencyInfo.Fiat(
                id = "usd",
                name = "US Dollar",
                symbol = "$",
                code = "USD",
                priceUsd = BigDecimal(1.0),
                tradable = true,
            ),
            CurrencyInfo.Fiat(
                id = "eur",
                name = "Euro",
                symbol = "€",
                code = "EUR",
                priceUsd = BigDecimal(1.2),
                tradable = false,
            )
        )

        override fun getCurrencies(
            tradableOnly: Boolean,
            currencyType: CurrencyType
        ): Flow<List<CurrencyInfo>> = when (currencyType) {
            CurrencyType.Crypto -> cryptoFlow.map { list ->
                list.filter { if (tradableOnly) it.tradable else true }
            }

            CurrencyType.Fiat -> fiatFlow.map { list ->
                list.filter { if (tradableOnly) it.tradable else true }
            }
        }

        override suspend fun insertData() {
            cryptoFlow.value = cryptos
            fiatFlow.value = fiats
        }

        override suspend fun clearData() {
            cryptoFlow.value = emptyList()
            fiatFlow.value = emptyList()
        }
    }
}