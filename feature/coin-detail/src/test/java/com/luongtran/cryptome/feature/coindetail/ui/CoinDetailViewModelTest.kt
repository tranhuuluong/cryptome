package com.luongtran.cryptome.feature.coindetail.ui

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.luongtran.cryptome.core.common.model.DataStateError
import com.luongtran.cryptome.core.common.model.DataStateSuccess
import com.luongtran.cryptome.core.common.model.Result
import com.luongtran.cryptome.core.common.model.StateLoading
import com.luongtran.cryptome.core.domain.CoinDetail
import com.luongtran.cryptome.core.domain.PriceHistory
import com.luongtran.cryptome.core.domain.PriceHistoryPeriod
import com.luongtran.cryptome.core.ui.utils.DefaultNumberFormatter
import com.luongtran.cryptome.core.ui.utils.NumberFormatter
import com.luongtran.cryptome.feature.coindetail.domain.CoinDetailRepository
import com.luongtran.cryptome.feature.coindetail.ui.model.CoinDetailUiState
import com.luongtran.cryptome.feature.coindetail.ui.model.CoinPriceChartUiState
import com.luongtran.cryptome.feature.coindetail.ui.model.PriceHistoryPeriodUi
import kotlinx.coroutines.Dispatchers
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
            assertTrue(awaitItem() is CoinDetailUiState.Loading)
            cancel()
        }
    }

    @Test
    fun initialPriceChart_isLoading() = runTest {
        viewModel.priceChartUiState.test {
            assertTrue(awaitItem() is CoinPriceChartUiState.Loading)
            cancel()
        }
    }

    @Test
    fun fetchCoinDetail_success_emitsLoadingThenSuccess() = runTest {
        viewModel.fetchCoinDetail("btc")
        viewModel.uiState.test {
            assertTrue(awaitItem() is CoinDetailUiState.Loading)
            val success = awaitItem() as CoinDetailUiState.Success
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

    @Test
    fun priceHistory_success_emitsLoadingThenSuccess() = runTest {
        viewModel.fetchCoinDetail("btc")
        viewModel.priceChartUiState.test {
            assertTrue(awaitItem() is CoinPriceChartUiState.Loading)
            val success = awaitItem() as CoinPriceChartUiState.Success
            assertEquals(listOf(1000.0, 1010.0, 1020.0), success.prices)
            assertEquals("+5.00%", success.priceChangePercent.formatted)
            cancel()
        }
    }

    @Test
    fun priceHistory_empty_emitsNotAvailable() = runTest {
        repository.priceHistoryMode = FakeCoinDetailRepository.PriceHistoryMode.EMPTY
        viewModel.fetchCoinDetail("btc")
        viewModel.priceChartUiState.test {
            assertTrue(awaitItem() is CoinPriceChartUiState.Loading)
            val na = awaitItem()
            assertTrue(na is CoinPriceChartUiState.NotAvailable)
            cancel()
        }
    }

    @Test
    fun priceHistory_error_emitsNotAvailable() = runTest {
        repository.priceHistoryMode = FakeCoinDetailRepository.PriceHistoryMode.ERROR
        viewModel.fetchCoinDetail("btc")
        viewModel.priceChartUiState.test {
            assertTrue(awaitItem() is CoinPriceChartUiState.Loading)
            val na = awaitItem()
            assertTrue(na is CoinPriceChartUiState.NotAvailable)
            cancel()
        }
    }

    @Test
    fun periodSelection_change_updatesState() = runTest {
        viewModel.periodSelectionUi.test {
            val initial = awaitItem()
            assertEquals(PriceHistoryPeriodUi.DAY, initial.selected)
            viewModel.onPricePeriodSelected(PriceHistoryPeriodUi.MONTH)
            val updated = awaitItem()
            assertEquals(PriceHistoryPeriodUi.MONTH, updated.selected)
            cancel()
        }
    }

    @Test
    fun retry_afterError_emitsErrorThenSuccess() = runTest {
        viewModel.fetchCoinDetail("retry")
        viewModel.uiState.test {
            assertTrue(awaitItem() is CoinDetailUiState.Loading)
            val firstError = awaitItem()
            assertTrue(firstError is CoinDetailUiState.Error)
            viewModel.retry()
            assertTrue(awaitItem() is CoinDetailUiState.Loading)
            val success = awaitItem()
            assertTrue(success is CoinDetailUiState.Success)
            assertEquals("retry", (success as CoinDetailUiState.Success).id)
            cancel()
        }
    }

    private class FakeCoinDetailRepository : CoinDetailRepository {
        enum class PriceHistoryMode { SUCCESS, EMPTY, ERROR }

        var priceHistoryMode = PriceHistoryMode.SUCCESS
        private var retryAttempts = 0

        override fun getCoinDetail(id: String): Flow<Result<CoinDetail>> = flow {
            emit(StateLoading)
            when (id) {
                "btc" -> emit(successDetail(id))
                "error" -> emit(DataStateError(Exception("fail")))
                "retry" -> {
                    if (retryAttempts == 0) {
                        retryAttempts++
                        emit(DataStateError(Exception("temporary")))
                    } else {
                        emit(successDetail(id))
                    }
                }

                else -> emit(DataStateError(Exception("not found")))
            }
        }

        override fun getPriceHistory(
            id: String,
            period: PriceHistoryPeriod,
            convert: String
        ): Flow<Result<PriceHistory>> = flow {
            emit(StateLoading)
            when (priceHistoryMode) {
                PriceHistoryMode.ERROR -> emit(DataStateError(Exception("ph error")))
                PriceHistoryMode.EMPTY -> emit(
                    DataStateSuccess(
                        PriceHistory(
                            prices = emptyList(),
                            changePercent = 0.0,
                            period = PriceHistoryPeriod.DAY,
                        )
                    )
                )

                PriceHistoryMode.SUCCESS -> emit(
                    DataStateSuccess(
                        PriceHistory(
                            prices = listOf(1000.0, 1010.0, 1020.0),
                            changePercent = 0.05,
                            period = PriceHistoryPeriod.DAY
                        )
                    )
                )
            }
        }

        private fun successDetail(id: String) = DataStateSuccess(
            CoinDetail(
                id = id,
                symbol = id.uppercase(),
                name = if (id == "btc") "Bitcoin" else "RetryCoin",
                slug = id,
                priceHistory = listOf(1.0, 2.0),
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
    }
}