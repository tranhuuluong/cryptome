package com.luongtran.cryptome.feature.coindetail.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.luongtran.cryptome.feature.coindetail.ui.component.CoinDetailContent
import com.luongtran.cryptome.feature.coindetail.ui.model.CoinDetailUiState
import org.koin.androidx.compose.koinViewModel

@Composable
fun CoinDetailScreen(
    id: String,
    name: String,
    modifier: Modifier = Modifier,
    viewModel: CoinDetailViewModel = koinViewModel(),
) {
    LaunchedEffect(id) {
        viewModel.fetchCoinDetail(id)
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    CoinDetailScreen(
        modifier = modifier,
        name = name,
        uiState = uiState,
    )
}

@Composable
private fun CoinDetailScreen(
    uiState: CoinDetailUiState,
    name: String,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = null,
                )
            }
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge
            )
        }
        Crossfade(
            modifier = Modifier.padding(horizontal = 16.dp),
            targetState = uiState,
        ) { state ->
            when (state) {
                is CoinDetailUiState.Loading -> CircularProgressIndicator(
                    modifier = Modifier.padding(32.dp)
                )

                is CoinDetailUiState.Success -> CoinDetailContent(
                    uiModel = state,
                )

                is CoinDetailUiState.Error -> {
//                    CoinDetailErrorScreen(modifier = modifier)
                }
            }

        }
    }
}