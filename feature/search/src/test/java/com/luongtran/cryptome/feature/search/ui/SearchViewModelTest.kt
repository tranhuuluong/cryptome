package com.luongtran.cryptome.feature.search.ui

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.luongtran.cryptome.core.domain.CurrencyInfo
import com.luongtran.cryptome.core.ui.utils.DefaultNumberFormatter
import com.luongtran.cryptome.core.ui.utils.NumberFormatter
import com.luongtran.cryptome.feature.search.domain.SearchRepository
import com.luongtran.cryptome.feature.search.ui.model.SearchUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
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

class SearchViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: FakeSearchRepository
    private lateinit var viewModel: SearchViewModel

    private val numberFormatter: NumberFormatter = DefaultNumberFormatter(
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

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = FakeSearchRepository()
        viewModel = SearchViewModel(SavedStateHandle(), repository, numberFormatter)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun initialState_emitsLoading_thenIdle() = runTest {
        viewModel.uiState.test {
            assertTrue(awaitItem() is SearchUiState.Loading)
            val idle = awaitItem()
            idle as SearchUiState.Idle
            assertEquals(1, idle.popularSearches.size)
            assertEquals("btc", idle.popularSearches.first().id)
            assertEquals("$1,000.12", idle.popularSearches.first().priceUsd.formatted)
            assertEquals("+2.50%", idle.popularSearches.first().changePercent24Hr.formatted)
            assertEquals(emptyList<String>(), idle.recentSearches)
            cancel()
        }
    }

    @Test
    fun searchQuery_emitsLoading_thenSuccess() = runTest {
        viewModel.onSearchQueryChange("btc")
        viewModel.uiState.test {
            assertTrue(awaitItem() is SearchUiState.Loading)
            val success = awaitItem()
            success as SearchUiState.Success
            assertEquals(1, success.currencies.size)
            val ui = success.currencies.first()
            assertEquals("btc", ui.id)
            assertEquals("$1,000.12", ui.priceUsd.formatted)
            cancel()
        }
    }

    @Test
    fun onSearchTriggered_savesRecentSearch() = runTest {
        viewModel.onSearchTriggered("eth")
        viewModel.onSearchTriggered("btc")
        advanceUntilIdle()
        repository.getRecentSearches(5).test {
            assertEquals(listOf("eth", "btc"), expectMostRecentItem())
            cancel()
        }
    }

    @Test
    fun clearRecentSearches_clearsRecentSearches() = runTest {
        viewModel.clearRecentSearches()
        viewModel.uiState.test {
            awaitItem() // Loading
            val idle = awaitItem() as SearchUiState.Idle
            assertTrue(idle.recentSearches.isEmpty())
            cancel()
        }
    }

    private class FakeSearchRepository : SearchRepository {
        private val recentSearchesFlow = MutableStateFlow(emptyList<String>())
        private val popularFlow = MutableStateFlow(
            listOf(
                CurrencyInfo.Crypto(
                    id = "btc",
                    name = "Bitcoin",
                    symbol = "BTC",
                    slug = "bitcoin",
                    priceUsd = 1000.12,
                    changePercent24Hr = 2.50,
                    marketCapUsd = 10000.0,
                    rank = 1,
                    tradable = true,
                )
            )
        )
        private val searchFlow = MutableStateFlow<List<CurrencyInfo>>(emptyList())

        override fun search(query: String): Flow<List<CurrencyInfo>> {
            searchFlow.value = popularFlow.value.filter { it.id.contains(query) }
            return searchFlow
        }

        override fun getRecentSearches(limit: Int): Flow<List<String>> = recentSearchesFlow

        override fun getPopularSearches(limit: Int): Flow<List<CurrencyInfo>> = popularFlow

        override suspend fun saveRecentSearch(query: String) {
            recentSearchesFlow.value += query
        }

        override suspend fun clearRecentSearches() {
            recentSearchesFlow.value = emptyList()
        }
    }
}