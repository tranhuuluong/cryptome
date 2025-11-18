package com.luongtran.cryptome.feature.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luongtran.cryptome.feature.home.domain.CurrencyRepository
import com.luongtran.cryptome.feature.home.ui.component.FilterPreviewData
import com.luongtran.cryptome.feature.home.ui.mapper.toUiModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: CurrencyRepository,
) : ViewModel() {

    val currencyList = repository.getAllCurrencies()
        .map { currencies -> currencies.map { it.toUiModel() } }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val filterUi = flowOf(FilterPreviewData.filter1)
        .stateIn(viewModelScope, SharingStarted.Lazily, FilterPreviewData.filter2)

    fun onClearDataClick() {
        viewModelScope.launch {
            repository.clearData()
        }
    }

    fun onInsertDataClick() {
        viewModelScope.launch {
            repository.insertData()
        }
    }
}