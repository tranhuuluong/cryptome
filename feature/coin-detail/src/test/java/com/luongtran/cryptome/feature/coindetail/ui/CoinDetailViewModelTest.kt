package com.luongtran.cryptome.feature.coindetail.ui

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.luongtran.cryptome.core.common.model.DataStateError
import com.luongtran.cryptome.core.common.model.DataStateSuccess
import com.luongtran.cryptome.core.common.model.Result
import com.luongtran.cryptome.core.common.model.StateLoading
import com.luongtran.cryptome.core.domain.CoinDetail
import com.luongtran.cryptome.core.ui.utils.DefaultNumberFormatter
import com.luongtran.cryptome.core.ui.utils.NumberFormatter
import com.luongtran.cryptome.feature.coindetail.domain.CoinDetailRepository
import com.luongtran.cryptome.feature.coindetail.ui.model.CoinDetailUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.text.NumberFormat
import java.util.Locale

class CoinDetailViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: FakeCoinDetailRepository
    private lateinit var viewModel: CoinDetailViewModel

    private val numberFormatter: NumberFormatter = DefaultNumberFormatter(
        priceFormatter = NumberFormat.getCurrencyInstance(Locale.US).apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
        },
        percentFormatter = NumberFormat.getInstance(Locale.US).apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
        },
        cryptoAmountFormatter = NumberFormat.getNumberInstance(Locale.US),
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = FakeCoinDetailRepository()
        viewModel = CoinDetailViewModel(SavedStateHandle(), repository, numberFormatter)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun initialState_isLoading() = runTest {
        viewModel.uiState.test {
            val first = awaitItem()
            assertTrue(first is CoinDetailUiState.Loading)
            cancel()
        }
    }

    @Test
    fun fetchCoinDetail_success_emitsLoadingThenSuccess() = runTest {
        viewModel.fetchCoinDetail("btc")
        viewModel.uiState.test {
            assertTrue(awaitItem() is CoinDetailUiState.Loading)
            val success = awaitItem()
            assertTrue(success is CoinDetailUiState.Success)
            success as CoinDetailUiState.Success
            assertEquals("btc", success.id)
            assertEquals("Bitcoin", success.name)
            assertEquals("BTC", success.symbol)
            assertEquals("$1,000.12", success.priceUsd.formatted)
            assertEquals("$500.00M", success.marketCapUsd.formatted)
            assertEquals("$123,456.00", success.volume24Hr.formatted)
            assertEquals("+2.50%", success.priceChange24h.formatted)
            assertEquals("$69,000.00", success.allTimeHigh.formatted)
            assertEquals("$65.00", success.allTimeLow.formatted)
            assertEquals("19M BTC", success.totalSupply.formatted)
            assertEquals("21M BTC", success.maxSupply.formatted)
            assertEquals("18.5M BTC", success.circulatingSupply.formatted)
            assertEquals(listOf(1.0, 2.0, 3.0), success.priceHistory)
            cancel()
        }
    }

    @Test
    fun fetchCoinDetail_error_emitsLoadingThenError() = runTest {
        viewModel.fetchCoinDetail("error")
        viewModel.uiState.test {
            assertTrue(awaitItem() is CoinDetailUiState.Loading)
            val error = awaitItem()
            assertTrue(error is CoinDetailUiState.Error)
            assertEquals("fail", (error as CoinDetailUiState.Error).message)
            cancel()
        }
    }

    private class FakeCoinDetailRepository : CoinDetailRepository {
        override fun getCoinDetail(id: String): Flow<Result<CoinDetail>> = flow {
            emit(StateLoading)
            delay(1)
            when (id) {
                "btc" -> emit(
                    DataStateSuccess(
                        CoinDetail(
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
                    )
                )

                "error" -> emit(DataStateError(Exception("fail")))
                else -> emit(DataStateError(Exception("not found")))
            }
        }
    }
}