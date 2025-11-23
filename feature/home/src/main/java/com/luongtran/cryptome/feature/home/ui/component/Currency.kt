package com.luongtran.cryptome.feature.home.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.luongtran.cryptome.core.designsystem.theme.AccentGreen
import com.luongtran.cryptome.core.designsystem.theme.AccentRed
import com.luongtran.cryptome.core.designsystem.theme.CryptomeTheme
import com.luongtran.cryptome.core.ui.model.DisplayableNumber
import com.luongtran.cryptome.feature.home.ui.model.CurrencyUi

@Composable
fun CurrencyList(
    currencies: List<CurrencyUi>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onCoinClick: (String, String) -> Unit = { _, _ -> },
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = WindowInsets.navigationBars.asPaddingValues(),
    ) {
        items(
            items = currencies,
            key = { currency -> currency.id }
        ) { currency ->
            when (currency) {
                is CurrencyUi.Crypto -> CryptoCurrencyItem(
                    modifier = Modifier.animateItem(),
                    contentPadding = contentPadding,
                    uiModel = currency,
                    onCoinClick = onCoinClick,
                )

                is CurrencyUi.Fiat -> FiatCurrencyItem(
                    modifier = Modifier.animateItem(),
                    contentPadding = contentPadding,
                    uiModel = currency,
                )
            }
        }
    }
}

@Composable
private fun CryptoCurrencyItem(
    uiModel: CurrencyUi.Crypto,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onCoinClick: (String, String) -> Unit = { _, _ -> },
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCoinClick(uiModel.id, uiModel.name) }
            .padding(contentPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val changePercent24Hr = uiModel.changePercent24Hr
        CurrencyIcon(iconRes = uiModel.iconRes)

        Row {
            CurrencyNameAndCode(
                modifier = Modifier.weight(1f),
                name = uiModel.name,
                code = uiModel.symbol,
                tradable = uiModel.tradable,
            )
            FakeChart(
                modifier = Modifier
                    .weight(1f)
                    .height(32.dp),
                lineColor = getStatusColor(changePercent24Hr.value)
            )
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                CurrencyPrice(formattedPrice = uiModel.price.formatted)
                ChangePercent(changePercent = changePercent24Hr)
            }
        }
    }
}

@Composable
private fun FiatCurrencyItem(
    uiModel: CurrencyUi.Fiat,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(contentPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CurrencyIcon(iconRes = uiModel.iconRes)
        CurrencyNameAndCode(
            modifier = Modifier.weight(1f),
            name = uiModel.name,
            code = uiModel.code,
            tradable = uiModel.tradable,
        )
        CurrencyPrice(
            modifier = Modifier.weight(1f),
            formattedPrice = uiModel.exchangeRateToUsd.formatted
        )
    }
}

@Composable
private fun CurrencyIcon(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int,
) {
    Image(
        painter = painterResource(iconRes),
        contentDescription = null,
        modifier = modifier.size(32.dp),
    )
}

@Composable
private fun CurrencyNameAndCode(
    modifier: Modifier = Modifier,
    name: String,
    code: String,
    tradable: Boolean,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = code.uppercase(),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = if (tradable) "Tradable" else "Not Tradable",
            style = MaterialTheme.typography.labelSmall,
            color = if (tradable) AccentGreen else AccentRed
        )
    }
}

@Composable
private fun CurrencyPrice(
    modifier: Modifier = Modifier,
    formattedPrice: String,
) {
    Text(
        modifier = modifier,
        text = formattedPrice,
        textAlign = TextAlign.End,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
private fun ChangePercent(
    modifier: Modifier = Modifier,
    changePercent: DisplayableNumber,
) {
    val (changePercent, changePercentFormatted) = changePercent
    Text(
        modifier = modifier,
        text = changePercentFormatted,
        style = MaterialTheme.typography.labelMedium,
        color = getStatusColor(changePercent)
    )
}

private fun getStatusColor(changePercent: Number) =
    if (changePercent.toDouble() >= 0.0) AccentGreen else AccentRed

@PreviewLightDark
@Composable
private fun CurrencyListPreview(
    @PreviewParameter(CurrencyListPreviewParamProvider::class)
    currencies: List<CurrencyUi>,
) {
    CryptomeTheme {
        Surface {
            CurrencyList(
                currencies = currencies,
                contentPadding = PaddingValues(16.dp)
            )
        }
    }
}