package com.luongtran.cryptome.feature.search.ui.model

interface SearchUiState {
    object Idle : SearchUiState
    object Loading : SearchUiState
    object Empty : SearchUiState
    data class Success(val currencies: List<SearchCurrencyUi>) : SearchUiState
}