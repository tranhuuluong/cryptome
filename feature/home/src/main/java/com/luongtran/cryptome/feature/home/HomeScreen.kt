package com.luongtran.cryptome.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.luongtran.cryptome.core.designsystem.theme.CryptomeTheme
import com.luongtran.cryptome.feature.home.component.ControlPanel
import com.luongtran.cryptome.feature.home.component.CurrencyList
import com.luongtran.cryptome.feature.home.component.CurrencyListPreviewData
import com.luongtran.cryptome.feature.home.component.Filter
import com.luongtran.cryptome.feature.home.component.FilterPreviewData
import com.luongtran.cryptome.feature.home.model.CurrencyUi
import com.luongtran.cryptome.feature.home.model.FilterOption
import com.luongtran.cryptome.feature.home.model.FilterUi
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
    onSearchBarClick: () -> Unit,
    onFilterChipClick: (FilterOption) -> Unit,
    onPurchasableCheckedChange: (Boolean) -> Unit,
) {
    val currencies by viewModel.currencyList.collectAsStateWithLifecycle()
    val filterUi by viewModel.filterUi.collectAsStateWithLifecycle()
    HomeScreen(
        modifier = modifier,
        currencies = currencies,
        filterUi = filterUi,
        onSearchBarClick = onSearchBarClick,
        onFilterChipClick = onFilterChipClick,
        onPurchasableCheckedChange = onPurchasableCheckedChange,
    )
}

@Composable
private fun HomeScreen(
    modifier: Modifier = Modifier,
    currencies: List<CurrencyUi>,
    filterUi: FilterUi,
    onSearchBarClick: () -> Unit = {},
    onFilterChipClick: (FilterOption) -> Unit = {},
    onPurchasableCheckedChange: (Boolean) -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SearchBar(onClick = onSearchBarClick)
            ControlPanel()
        }
        Filter(
            uiModel = filterUi,
            contentPadding = PaddingValues(horizontal = 16.dp),
            onFilterChipClick = onFilterChipClick,
            onPurchasableCheckedChange = onPurchasableCheckedChange,
        )
        CurrencyList(
            currencies = currencies,
            contentPadding = PaddingValues(horizontal = 16.dp)
        )
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
            text = "Search coin",
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@PreviewLightDark
@Composable
private fun HomeScreenPreview() {
    CryptomeTheme {
        Surface {
            HomeScreen(
                currencies = CurrencyListPreviewData.data,
                filterUi = FilterPreviewData.filter1
            )
        }
    }
}