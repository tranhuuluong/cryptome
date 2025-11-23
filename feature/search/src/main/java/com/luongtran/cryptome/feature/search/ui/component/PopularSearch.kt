package com.luongtran.cryptome.feature.search.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.luongtran.cryptome.core.designsystem.theme.AccentGreen
import com.luongtran.cryptome.core.designsystem.theme.AccentRed
import com.luongtran.cryptome.core.designsystem.theme.CryptomeTheme
import com.luongtran.cryptome.core.ui.model.DisplayableNumber
import com.luongtran.cryptome.feature.search.ui.model.PopularSearchUi
import java.math.BigDecimal
import com.luongtran.cryptome.core.ui.R as RUi

@Composable
fun PopularSearch(
    uiModel: PopularSearchUi,
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
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        val rank = uiModel.rank
        Text(
            modifier = modifier.defaultMinSize(minWidth = 24.dp),
            text = "$rank",
            style = MaterialTheme.typography.titleLarge.copy(
                color = when {
                    rank > 3 -> MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                    else -> MaterialTheme.colorScheme.tertiary
                },
                textAlign = TextAlign.Center,
            ),
        )
        Image(
            painter = painterResource(uiModel.iconRes),
            contentDescription = null,
            modifier = Modifier.size(32.dp),
        )
        Text(
            text = uiModel.code,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            val (changePercent, changePercentFormatted) = uiModel.changePercent24Hr
            val color = if (changePercent.toDouble() >= 0.0) AccentGreen else AccentRed
            Text(
                modifier = modifier,
                text = uiModel.priceUsd.formatted,
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                modifier = modifier,
                text = changePercentFormatted,
                style = MaterialTheme.typography.labelMedium,
                color = color
            )
        }
    }
}


@PreviewLightDark
@Composable
private fun PopularSearchPreview() {
    val uiModel = PopularSearchUi(
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
    CryptomeTheme {
        Surface {
            PopularSearch(
                uiModel = uiModel,
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}