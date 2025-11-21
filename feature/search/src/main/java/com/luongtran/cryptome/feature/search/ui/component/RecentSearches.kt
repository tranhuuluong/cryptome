package com.luongtran.cryptome.feature.search.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.luongtran.cryptome.core.designsystem.component.CryptomeChip
import com.luongtran.cryptome.core.designsystem.theme.CryptomeTheme
import com.luongtran.cryptome.feature.search.R

@Composable
fun RecentSearches(
    recentSearches: List<String>,
    modifier: Modifier = Modifier,
    onRecentSearchClick: (String) -> Unit = {},
    onClearRecentSearchesClick: () -> Unit = {},
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.recent_searches),
                style = MaterialTheme.typography.labelLarge,
            )
            ClearRecentSearchesButton(onClick = onClearRecentSearchesClick)
        }
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            recentSearches.fastForEach { query ->
                CryptomeChip(
                    label = query,
                    onClick = { onRecentSearchClick(query) },
                )
            }
        }
    }
}

@Composable
private fun ClearRecentSearchesButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides 0.dp) {
        IconButton(
            modifier = modifier,
            onClick = onClick
        ) {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = null,
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun RecentSearchesPreview() {
    CryptomeTheme {
        Surface {
            RecentSearches(
                modifier = Modifier.padding(16.dp),
                recentSearches = listOf("Bitcoin", "Ethereum", "Tether", "BNB", "USD Coin"),
            )
        }
    }
}