package com.luongtran.cryptome.feature.search.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luongtran.cryptome.feature.search.domain.SearchRepository
import com.luongtran.cryptome.feature.search.ui.mapper.toSearchUiState
import com.luongtran.cryptome.feature.search.ui.model.SearchUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SearchViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val searchRepository: SearchRepository
) : ViewModel() {
    val searchQuery = savedStateHandle.getStateFlow(SEARCH_QUERY, "")

    val uiState = searchQuery
        .flatMapLatest { query ->
            when {
                query.isEmpty() -> flowOf(SearchUiState.Idle)
                else -> flow {
                    emit(SearchUiState.Loading)
                    emitAll(
                        searchRepository.search(query).map { currencies ->
                            currencies.toSearchUiState()
                        }
                    )
                }.debounce(SEARCH_DEBOUNCE_DURATION)
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, SearchUiState.Idle)

    val recentSearches = searchRepository.getRecentSearches(RECENT_SEARCHES_LIMIT)
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())


    fun onSearchQueryChange(query: String) {
        savedStateHandle[SEARCH_QUERY] = query
    }

    fun onSearchTriggered(query: String) {
        savedStateHandle[SEARCH_QUERY] = query
        viewModelScope.launch {
            searchRepository.saveRecentSearch(query)
        }
    }

    fun clearRecentSearches() {
        viewModelScope.launch {
            searchRepository.clearRecentSearches()
        }
    }


    companion object {
        private const val SEARCH_QUERY = "searchQuery"
        private const val SEARCH_DEBOUNCE_DURATION = 200L
        private const val RECENT_SEARCHES_LIMIT = 10
    }
}