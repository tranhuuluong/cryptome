package com.luongtran.cryptome.feature.home.ui.model

sealed interface HomeUiState {
    object Empty : HomeUiState
    object Loading : HomeUiState
    data class Success(
        val currencies: List<CurrencyUi>,
        val filterUi: FilterUi,
    ) : HomeUiState

}