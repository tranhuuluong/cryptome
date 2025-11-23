package com.luongtran.cryptome.feature.search.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
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
    onBackClick: () -> Unit,
    onCoinClick: (String, String) -> Unit,
) {
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    SearchScreen(
        modifier = modifier,
        searchQuery = searchQuery,
        uiState = uiState,
        onBackClick = onBackClick,
        onSearchQueryChanged = viewModel::onSearchQueryChange,
        onSearchTriggered = viewModel::onSearchTriggered,
        onClearRecentSearchesClick = viewModel::clearRecentSearches,
        onCoinClick = onCoinClick,
    )
}

@Composable
private fun SearchScreen(
    modifier: Modifier = Modifier,
    searchQuery: String,
    uiState: SearchUiState,
    onBackClick: () -> Unit = {},
    onSearchQueryChanged: (String) -> Unit = {},
    onSearchTriggered: (String) -> Unit = {},
    onClearRecentSearchesClick: () -> Unit = {},
    onCoinClick: (String, String) -> Unit = { _, _ -> },
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val hideKeyBoard = { keyboardController?.hide() }
    val onSearchExplicitlyTriggered: (String) -> Unit = {
        focusManager.clearFocus()
        hideKeyBoard()
        onSearchTriggered(it)
    }
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 12.dp,
                    bottom = 12.dp,
                    start = 4.dp,
                    end = 16.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    onBackClick()
                    hideKeyBoard()
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = null,
                )
            }
            SearchBar(
                modifier = Modifier.weight(1f),
                searchQuery = searchQuery,
                onSearchQueryChanged = onSearchQueryChanged,
                onSearchTriggered = onSearchExplicitlyTriggered,
            )
        }
        val lazyListState = rememberLazyListState()
        LaunchedEffect(lazyListState) {
            snapshotFlow { lazyListState.isScrollInProgress }
                .collect { isScrolling ->
                    if (isScrolling) {
                        hideKeyBoard()
                    }
                }
        }
        when (uiState) {
            is SearchUiState.Idle -> LazyColumn(
                state = lazyListState,
                contentPadding = WindowInsets.navigationBars.asPaddingValues(),
            ) {
                val recentSearches = uiState.recentSearches
                if (recentSearches.isNotEmpty()) {
                    item {
                        RecentSearches(
                            modifier = Modifier
                                .animateItem()
                                .padding(horizontal = 16.dp),
                            recentSearches = recentSearches,
                            onRecentSearchClick = onSearchExplicitlyTriggered,
                            onClearRecentSearchesClick = onClearRecentSearchesClick,
                        )
                    }
                    item {
                        Spacer(Modifier.height(16.dp))
                    }
                }

                val popularSearches = uiState.popularSearches
                if (popularSearches.isNotEmpty()) {
                    item {
                        Text(
                            modifier = Modifier
                                .animateItem()
                                .padding(vertical = 8.dp, horizontal = 16.dp),
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
                            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
                            onCoinClick = onCoinClick,
                        )
                    }
                }
            }

            SearchUiState.Loading -> CircularProgressIndicator()
            is SearchUiState.Empty -> SearchEmptyView(query = uiState.searchQuery)
            is SearchUiState.Success -> SearchItems(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                items = uiState.currencies,
                searchQuery = searchQuery,
                onCoinClick = onCoinClick,
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun SearchScreenPreview() {
    CryptomeTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            SearchScreen(
                searchQuery = "",
                uiState = SearchUiState.Idle(
                    recentSearches = listOf("Bitcoin", "Ethereum", "Dogecoin"),
                    popularSearches = listOf(
                        PopularSearchUi(
                            id = "bitcoin",
                            code = "BTC",
                            name = "Bitcoin",
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