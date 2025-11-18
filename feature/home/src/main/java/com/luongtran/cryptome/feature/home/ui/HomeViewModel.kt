package com.luongtran.cryptome.feature.home.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luongtran.cryptome.core.domain.CurrencyType
import com.luongtran.cryptome.feature.home.domain.CurrencyRepository
import com.luongtran.cryptome.feature.home.ui.mapper.toHomeUiState
import com.luongtran.cryptome.feature.home.ui.model.FilterOption
import com.luongtran.cryptome.feature.home.ui.model.FilterUi
import com.luongtran.cryptome.feature.home.ui.model.HomeUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.Serializable

class HomeViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val repository: CurrencyRepository,
) : ViewModel() {
    private val userInput = savedStateHandle.getStateFlow(USER_INPUT, UserInput())

    val uiState = userInput.flatMapLatest { (selectedFilterOption, showTradable) ->
        val currencyType = when (selectedFilterOption) {
            FilterOption.All -> CurrencyType.All
            FilterOption.Crypto -> CurrencyType.Crypto
            FilterOption.Fiat -> CurrencyType.Fiat
        }
        repository.getCurrencies(tradableOnly = showTradable, currencyType = currencyType)
            .map { currencies ->
                val filterUi = FilterUi.default().copy(
                    selectedOption = selectedFilterOption,
                    showTradable = showTradable,
                )
                currencies.toHomeUiState(filterUi)
            }
    }
        .stateIn(viewModelScope, SharingStarted.Lazily, HomeUiState.Loading)

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

    fun onFilterOptionClick(filterOption: FilterOption) {
        savedStateHandle[USER_INPUT] = userInput.value.copy(
            selectedFilterOption = filterOption,
        )
    }

    fun onTradableCheckedChange() {
        val userInput = userInput.value
        savedStateHandle[USER_INPUT] = userInput.copy(
            showTradable = !userInput.showTradable,
        )
    }

    private data class UserInput(
        val selectedFilterOption: FilterOption = FilterOption.All,
        val showTradable: Boolean = false,
    ) : Serializable

    companion object {
        private const val USER_INPUT = "userInput"
    }
}