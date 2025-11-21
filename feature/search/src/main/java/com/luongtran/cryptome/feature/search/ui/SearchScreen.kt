package com.luongtran.cryptome.feature.search.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.luongtran.cryptome.core.designsystem.theme.CryptomeTheme
import com.luongtran.cryptome.core.ui.model.DisplayableNumber
import com.luongtran.cryptome.feature.search.R
import com.luongtran.cryptome.feature.search.ui.component.PopularSearch
import com.luongtran.cryptome.feature.search.ui.component.RecentSearches
import com.luongtran.cryptome.feature.search.ui.component.SearchBar
import com.luongtran.cryptome.feature.search.ui.component.SearchEmptyView
import com.luongtran.cryptome.feature.search.ui.component.SearchItems
import com.luongtran.cryptome.feature.search.ui.model.PopularSearchUi
import com.luongtran.cryptome.feature.search.ui.model.SearchUiState
import org.koin.androidx.compose.koinViewModel
import java.math.BigDecimal
import com.luongtran.cryptome.core.ui.R as RUi


@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = koinViewModel(),
) {
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    SearchScreen(
        modifier = modifier,
        searchQuery = searchQuery,
        uiState = uiState,
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
            is SearchUiState.Idle -> LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp),
            ) {
                val recentSearches = uiState.recentSearches
                if (recentSearches.isNotEmpty()) {
                    item {
                        RecentSearches(
                            modifier = Modifier.animateItem(),
                            recentSearches = recentSearches,
                            onRecentSearchClick = onSearchTriggered,
                            onClearRecentSearchesClick = onClearRecentSearchesClick,
                        )
                    }
                }

                val popularSearches = uiState.popularSearches
                if (popularSearches.isNotEmpty()) {
                    item {
                        Text(
                            modifier = Modifier
                                .animateItem()
                                .padding(top = 24.dp, bottom = 8.dp),
                            text = stringResource(R.string.popular_searches),
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }
                    items(
                        items = popularSearches,
                        key = { it.id }
                    ) { popularSearch ->
                        PopularSearch(
                            modifier = Modifier.animateItem(),
                            uiModel = popularSearch,
                            contentPadding = PaddingValues(vertical = 8.dp),
                        )
                    }
                }
            }

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
                uiState = SearchUiState.Idle(
                    recentSearches = listOf("Bitcoin", "Ethereum", "Dogecoin"),
                    popularSearches = listOf(
                        PopularSearchUi(
                            id = "bitcoin",
                            code = "BTC",
                            priceUsd = DisplayableNumber(
                                value = BigDecimal("90000.00"),
                                formatted = "$90,000.00"
                            ),
                            changePercent24Hr = DisplayableNumber(
                                value = BigDecimal("2.5"),
                                formatted = "+2.5%"
                            ),
                            rank = 1,
                            iconRes = RUi.drawable.btc,
                        )
                    ),
                ),
            )
        }
    }
}