package com.luongtran.cryptome.feature.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.luongtran.cryptome.core.designsystem.theme.CryptomeTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = koinViewModel(),
) {
    SearchScreen(modifier = modifier)
}

@Composable
private fun SearchScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text("Search screen")
    }
}

@Preview
@Composable
private fun SearchScreenPreview() {
    CryptomeTheme {
        SearchScreen()
    }
}