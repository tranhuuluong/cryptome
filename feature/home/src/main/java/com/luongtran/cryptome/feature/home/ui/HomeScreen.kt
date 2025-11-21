package com.luongtran.cryptome.feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.luongtran.cryptome.core.designsystem.theme.CryptomeTheme
import com.luongtran.cryptome.feature.home.ui.component.ControlPanel
import com.luongtran.cryptome.feature.home.ui.component.CurrencyList
import com.luongtran.cryptome.feature.home.ui.component.Filter
import com.luongtran.cryptome.feature.home.ui.component.HomeEmptyView
import com.luongtran.cryptome.feature.home.ui.model.FilterOption
import com.luongtran.cryptome.feature.home.ui.model.HomeUiState
import org.koin.androidx.compose.koinViewModel
import com.luongtran.cryptome.core.ui.R as RUi

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
    onSearchBarClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreen(
        modifier = modifier,
        uiState = uiState,
        onSearchBarClick = onSearchBarClick,
        onClearDataClick = viewModel::onClearDataClick,
        onInsertDataClick = viewModel::onInsertDataClick,
        onFilterOptionClick = viewModel::onFilterOptionClick,
        onTradableClick = viewModel::onTradableCheckedChange
    )
}

@Composable
private fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    onSearchBarClick: () -> Unit = {},
    onClearDataClick: () -> Unit = {},
    onInsertDataClick: () -> Unit = {},
    onFilterOptionClick: (FilterOption) -> Unit = {},
    onTradableClick: () -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val navigationBarPadding = WindowInsets.navigationBars
            .asPaddingValues()
            .calculateBottomPadding()
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SearchBar(onClick = onSearchBarClick)
            ControlPanel(
                onClearDataClick = onClearDataClick,
                onInsertDataClick = onInsertDataClick
            )
        }
        when (uiState) {
            is HomeUiState.Loading -> CircularProgressIndicator()
            is HomeUiState.Empty -> {
                Filter(
                    uiModel = uiState.filterUi,
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    onFilterOptionClick = onFilterOptionClick,
                    onTradableClick = onTradableClick,
                )
                HomeEmptyView()
            }

            is HomeUiState.Success -> {
                Filter(
                    uiModel = uiState.filterUi,
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    onFilterOptionClick = onFilterOptionClick,
                    onTradableClick = onTradableClick,
                )
                CurrencyList(
                    currencies = uiState.currencies,
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = navigationBarPadding,
                    )
                )
            }
        }
    }
}

@Composable
private fun SearchBar(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val shape = RoundedCornerShape(32.dp)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = shape,
            )
            .shadow(
                elevation = 10.dp,
                shape = shape,
                ambientColor = MaterialTheme.colorScheme.tertiary,
                spotColor = MaterialTheme.colorScheme.tertiaryContainer,
            )
            .background(MaterialTheme.colorScheme.surface)
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp, horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Outlined.Search,
            contentDescription = "Search",
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = stringResource(RUi.string.search_hint),
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@PreviewLightDark
@Composable
private fun HomeScreenPreview() {
    CryptomeTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            HomeScreen(uiState = HomeUiState.Loading)
        }
    }
}