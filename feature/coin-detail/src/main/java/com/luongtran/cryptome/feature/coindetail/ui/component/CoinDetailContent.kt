package com.luongtran.cryptome.feature.coindetail.ui.component

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.luongtran.cryptome.core.designsystem.component.CryptomeFilterChip
import com.luongtran.cryptome.core.designsystem.theme.CryptomeTheme
import com.luongtran.cryptome.core.ui.model.DisplayableNumber
import com.luongtran.cryptome.feature.coindetail.R
import com.luongtran.cryptome.feature.coindetail.ui.model.CoinDetailUiState
import com.luongtran.cryptome.feature.coindetail.ui.model.CoinPriceChartUiState
import com.luongtran.cryptome.feature.coindetail.ui.model.PeriodSelectionUi
import com.luongtran.cryptome.feature.coindetail.ui.model.PriceHistoryPeriodUi
import com.luongtran.cryptome.core.ui.R as RUi

@Composable
fun CoinDetailContent(
    uiModel: CoinDetailUiState.Success,
    priceChartUiState: CoinPriceChartUiState,
    periodSelectionUi: PeriodSelectionUi,
    modifier: Modifier = Modifier,
    onPeriodSelected: (PriceHistoryPeriodUi) -> Unit = {},
) {
    val (marketCap, marketCapFormatted) = uiModel.marketCapUsd
    val (volume24h, volume24hFormatted) = uiModel.volume24Hr
    val (circulatingSupply, circulatingSupplyFormatted) = uiModel.circulatingSupply
    val (allTimeHigh, allTimeHighFormatted) = uiModel.allTimeHigh
    val (allTimeLow, allTimeLowFormatted) = uiModel.allTimeLow
    val (totalSupply, totalSupplyFormatted) = uiModel.totalSupply
    val (maxSupply, maxSupplyFormatted) = uiModel.maxSupply
    val rank = uiModel.rank

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(uiModel.iconRes),
                contentDescription = null,
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    modifier = Modifier.alignBy(LastBaseline),
                    text = uiModel.name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    modifier = Modifier.alignBy(LastBaseline),
                    text = uiModel.symbol,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Text(
            text = uiModel.priceUsd.formatted,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentAlignment = Alignment.Center
        ) {
            Crossfade(priceChartUiState) { state ->
                when (state) {
                    is CoinPriceChartUiState.Loading -> Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }

                    is CoinPriceChartUiState.NotAvailable -> Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = stringResource(R.string.no_chart_data_available),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    is CoinPriceChartUiState.Success -> PriceHistoryLineChart(
                        modifier = Modifier.fillMaxSize(),
                        uiModel = state,
                    )
                }
            }
        }

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = periodSelectionUi.periods,
                key = { it },
            ) { period ->
                CryptomeFilterChip(
                    label = stringResource(period.resId),
                    selected = period == periodSelectionUi.selected,
                    onSelectedChange = { onPeriodSelected(period) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.market_data),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            maxItemsInEachRow = 2,
        ) {
            if (marketCap.toDouble() > 0.0) {
                MarketDataContent(
                    modifier = Modifier.weight(1f),
                    label = stringResource(R.string.market_cap),
                    value = marketCapFormatted,
                )
            }
            if (rank > 0) {
                MarketDataContent(
                    modifier = Modifier.weight(1f),
                    label = stringResource(R.string.rank),
                    value = "#$rank",
                )
            }
            if (volume24h.toDouble() > 0.0) {
                MarketDataContent(
                    modifier = Modifier.weight(1f),
                    label = stringResource(R.string.volume_24h),
                    value = volume24hFormatted,
                )
            }
            if (circulatingSupply.toDouble() > 0.0) {
                MarketDataContent(
                    modifier = Modifier.weight(1f),
                    label = stringResource(R.string.circulating_supply),
                    value = circulatingSupplyFormatted,
                )
            }
            if (allTimeHigh.toDouble() > 0.0) {
                MarketDataContent(
                    modifier = Modifier.weight(1f),
                    label = stringResource(R.string.all_time_high),
                    value = allTimeHighFormatted,
                )
            }
            if (allTimeLow.toDouble() > 0.0) {
                MarketDataContent(
                    modifier = Modifier.weight(1f),
                    label = stringResource(R.string.all_time_low),
                    value = allTimeLowFormatted,
                )
            }
            if (totalSupply.toDouble() > 0.0) {
                MarketDataContent(
                    modifier = Modifier.weight(1f),
                    label = stringResource(R.string.total_supply),
                    value = totalSupplyFormatted,
                )
            }
            if (maxSupply.toDouble() > 0.0) {
                MarketDataContent(
                    modifier = Modifier.weight(1f),
                    label = stringResource(R.string.max_supply),
                    value = maxSupplyFormatted,
                )
            }
        }
    }
}

@Composable
private fun MarketDataContent(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@PreviewLightDark
@Composable
private fun CoinDetailContentPreview() {
    CryptomeTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            CoinDetailContent(
                modifier = Modifier.padding(16.dp),
                uiModel = CoinDetailUiState.Success(
                    id = "bitcoin",
                    name = "Bitcoin",
                    symbol = "BTC",
                    iconRes = RUi.drawable.btc,
                    rank = 1,
                    marketCapUsd = DisplayableNumber(
                        value = 600000000000,
                        formatted = "$600,000,000,000"
                    ),
                    volume24Hr = DisplayableNumber(
                        value = 35000000000,
                        formatted = "$35,000,000,000"
                    ),
                    circulatingSupply = DisplayableNumber(
                        value = 19000000,
                        formatted = "19,000,000 BTC"
                    ),
                    totalSupply = DisplayableNumber(
                        value = 21000000,
                        formatted = "21,000,000 BTC"
                    ),
                    maxSupply = DisplayableNumber(
                        value = 21000000,
                        formatted = "21,000,000 BTC"
                    ),
                    allTimeHigh = DisplayableNumber(
                        value = 69000,
                        formatted = "$69,000"
                    ),
                    allTimeLow = DisplayableNumber(
                        value = 67,
                        formatted = "$67"
                    ),
                    priceUsd = DisplayableNumber(
                        value = 32000,
                        formatted = "$32,000"
                    ),
                    priceChange24h = DisplayableNumber(
                        value = -2.5,
                        formatted = "-2.5%"
                    ),
                ),
                priceChartUiState = CoinPriceChartUiState.Loading,
                periodSelectionUi = PeriodSelectionUi.default()
            )
        }
    }
}