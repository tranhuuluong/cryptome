package com.luongtran.cryptome.feature.coindetail.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luongtran.cryptome.core.ui.utils.NumberFormatter
import com.luongtran.cryptome.feature.coindetail.domain.CoinDetailRepository
import com.luongtran.cryptome.feature.coindetail.ui.mapper.toCoinDetailUiState
import com.luongtran.cryptome.feature.coindetail.ui.model.CoinDetailUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class CoinDetailViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val repository: CoinDetailRepository,
    private val numberFormatter: NumberFormatter,
) : ViewModel() {

    val uiState = savedStateHandle
        .getStateFlow(COIN_ID, "")
        .flatMapLatest { repository.getCoinDetail(it) }
        .map { result -> result.toCoinDetailUiState(numberFormatter) }
        .stateIn(viewModelScope, SharingStarted.Lazily, CoinDetailUiState.Loading)

    fun fetchCoinDetail(id: String) {
        savedStateHandle[COIN_ID] = id
    }

    companion object {
        private const val COIN_ID = "id"
    }
}