package com.luongtran.cryptome.feature.search.ui.model

interface SearchUiState {
    object Loading : SearchUiState
    object Empty : SearchUiState

    data class Idle(
        val popularSearches: List<PopularSearchUi>,
        val recentSearches: List<String>,
    ) : SearchUiState

    data class Success(val currencies: List<SearchCurrencyUi>) : SearchUiState
}