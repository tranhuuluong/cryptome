package com.luongtran.cryptome.feature.search.ui.model

interface SearchUiState {
    object Loading : SearchUiState
    data class Empty(val searchQuery: String) : SearchUiState

    data class Idle(
        val popularSearches: List<PopularSearchUi>,
        val recentSearches: List<String>,
    ) : SearchUiState

    data class Success(val currencies: List<SearchCurrencyUi>) : SearchUiState
}