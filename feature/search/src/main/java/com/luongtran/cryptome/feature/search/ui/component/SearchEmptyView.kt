package com.luongtran.cryptome.feature.search.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.luongtran.cryptome.core.designsystem.theme.CryptomeTheme
import com.luongtran.cryptome.feature.search.R

@Composable
fun SearchEmptyView(
    query: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Icon(
            imageVector = Icons.Default.SearchOff,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(64.dp)
        )
        Text(
            text = stringResource(R.string.no_results_found, query),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@PreviewLightDark
@Composable
private fun SearchEmptyViewPreview(modifier: Modifier = Modifier) {
    CryptomeTheme {
        Surface {
            SearchEmptyView(
                modifier = modifier,
                query = "btc",
            )
        }
    }
}