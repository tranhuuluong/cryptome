package com.luongtran.cryptome.feature.coindetail.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luongtran.cryptome.core.domain.PriceHistoryPeriod
import com.luongtran.cryptome.core.ui.utils.NumberFormatter
import com.luongtran.cryptome.feature.coindetail.domain.CoinDetailRepository
import com.luongtran.cryptome.feature.coindetail.ui.mapper.toCoinDetailUiState
import com.luongtran.cryptome.feature.coindetail.ui.mapper.toPriceChartUiState
import com.luongtran.cryptome.feature.coindetail.ui.model.CoinDetailUiState
import com.luongtran.cryptome.feature.coindetail.ui.model.CoinPriceChartUiState
import com.luongtran.cryptome.feature.coindetail.ui.model.PeriodSelectionUi
import com.luongtran.cryptome.feature.coindetail.ui.model.PriceHistoryPeriodUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn

class CoinDetailViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val repository: CoinDetailRepository,
    private val numberFormatter: NumberFormatter,
) : ViewModel() {

    private val coinIdFlow = savedStateHandle.getStateFlow(COIN_ID, "")
    private val periodFlow = savedStateHandle.getStateFlow(PRICE_PERIOD, PriceHistoryPeriodUi.DAY)
    private val fetchCoinDetailTrigger = Channel<Unit>()

    val uiState = merge(flowOf(Unit), fetchCoinDetailTrigger.receiveAsFlow())
        .flatMapLatest { coinIdFlow }
        .flatMapLatest { repository.getCoinDetail(it) }
        .map { result -> result.toCoinDetailUiState(numberFormatter) }
        .stateIn(viewModelScope, SharingStarted.Lazily, CoinDetailUiState.Loading)

    val priceChartUiState = combineTransform(
        coinIdFlow,
        savedStateHandle.getStateFlow(PRICE_PERIOD, PriceHistoryPeriodUi.DAY)
    ) { id, period ->
        emitAll(
            repository.getPriceHistory(id = id, period = period.toPeriodDomain()).map { result ->
                result.toPriceChartUiState(numberFormatter)
            }
        )
    }
        .stateIn(viewModelScope, SharingStarted.Lazily, CoinPriceChartUiState.Loading)

    val periodSelectionUi = periodFlow
        .map { selectedPeriod ->
            PeriodSelectionUi.default().copy(selected = selectedPeriod)
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, PeriodSelectionUi.default())


    fun fetchCoinDetail(id: String) {
        savedStateHandle[COIN_ID] = id
    }

    fun retry() {
        fetchCoinDetailTrigger.trySend(Unit)
    }

    fun onPricePeriodSelected(period: PriceHistoryPeriodUi) {
        savedStateHandle[PRICE_PERIOD] = period
    }

    private fun PriceHistoryPeriodUi.toPeriodDomain(): PriceHistoryPeriod = when (this) {
        PriceHistoryPeriodUi.HOUR -> PriceHistoryPeriod.HOUR
        PriceHistoryPeriodUi.DAY -> PriceHistoryPeriod.DAY
        PriceHistoryPeriodUi.WEEK -> PriceHistoryPeriod.WEEK
        PriceHistoryPeriodUi.MONTH -> PriceHistoryPeriod.MONTH
        PriceHistoryPeriodUi.THREE_MONTHS -> PriceHistoryPeriod.THREE_MONTHS
        PriceHistoryPeriodUi.SIX_MONTHS -> PriceHistoryPeriod.SIX_MONTHS
        PriceHistoryPeriodUi.YEAR -> PriceHistoryPeriod.YEAR
        PriceHistoryPeriodUi.ALL -> PriceHistoryPeriod.ALL
    }

    companion object {
        private const val COIN_ID = "id"
        private const val PRICE_PERIOD = "pricePeriod"
    }
}