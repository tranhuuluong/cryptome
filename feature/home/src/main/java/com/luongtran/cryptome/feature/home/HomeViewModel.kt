package com.luongtran.cryptome.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luongtran.cryptome.feature.home.component.CurrencyListPreviewData
import com.luongtran.cryptome.feature.home.component.FilterPreviewData
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn

class HomeViewModel : ViewModel() {

    val currencyList = flowOf(CurrencyListPreviewData.data)
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val filterUi = flowOf(FilterPreviewData.filter1)
        .stateIn(viewModelScope, SharingStarted.Lazily, FilterPreviewData.filter2)
}