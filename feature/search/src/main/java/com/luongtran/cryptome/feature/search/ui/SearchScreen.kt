package com.luongtran.cryptome.feature.search.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.luongtran.cryptome.core.designsystem.theme.CryptomeTheme
import com.luongtran.cryptome.feature.search.ui.component.RecentSearches
import com.luongtran.cryptome.feature.search.ui.component.SearchBar
import com.luongtran.cryptome.feature.search.ui.component.SearchEmptyView
import com.luongtran.cryptome.feature.search.ui.component.SearchItems
import com.luongtran.cryptome.feature.search.ui.model.SearchUiState
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = koinViewModel(),
) {
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val recentSearches by viewModel.recentSearches.collectAsStateWithLifecycle()
    SearchScreen(
        modifier = modifier,
        searchQuery = searchQuery,
        uiState = uiState,
        recentSearches = recentSearches,
        onSearchQueryChanged = viewModel::onSearchQueryChange,
        onSearchTriggered = viewModel::onSearchTriggered,
        onClearRecentSearchesClick = viewModel::clearRecentSearches,
    )
}

@Composable
private fun SearchScreen(
    modifier: Modifier = Modifier,
    searchQuery: String,
    uiState: SearchUiState,
    recentSearches: List<String>,
    onSearchQueryChanged: (String) -> Unit = {},
    onSearchTriggered: (String) -> Unit = {},
    onClearRecentSearchesClick: () -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            searchQuery = searchQuery,
            onSearchQueryChanged = onSearchQueryChanged,
            onSearchTriggered = onSearchTriggered,
        )
        when (uiState) {
            SearchUiState.Idle -> RecentSearches(
                modifier = Modifier.padding(horizontal = 16.dp),
                recentSearches = recentSearches,
                onRecentSearchClick = onSearchTriggered,
                onClearRecentSearchesClick = onClearRecentSearchesClick,
            )

            SearchUiState.Loading -> CircularProgressIndicator()
            SearchUiState.Empty -> SearchEmptyView(query = searchQuery)
            is SearchUiState.Success -> SearchItems(
                contentPadding = PaddingValues(16.dp),
                items = uiState.currencies
            )
        }
    }
}

@Preview
@Composable
private fun SearchScreenPreview() {
    CryptomeTheme {
        Surface {
            SearchScreen(
                searchQuery = "",
                recentSearches = listOf("Bitcoin", "Ethereum", "Dogecoin"),
                uiState = SearchUiState.Idle,
            )
        }
    }
}