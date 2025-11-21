package com.luongtran.cryptome.feature.search.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.luongtran.cryptome.core.designsystem.theme.CryptomeTheme
import com.luongtran.cryptome.core.designsystem.theme.SearchHighlightBackground
import com.luongtran.cryptome.core.ui.model.DisplayableNumber
import com.luongtran.cryptome.feature.search.ui.model.SearchCurrencyUi
import java.math.BigDecimal
import com.luongtran.cryptome.core.ui.R as RUi

@Composable
fun SearchItems(
    items: List<SearchCurrencyUi>,
    searchQuery: String,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
    ) {
        items(
            items = items,
            key = { item -> item.id }
        ) { item ->
            SearchItem(
                modifier = Modifier.animateItem(),
                searchQuery = searchQuery,
                uiModel = item,
            )
        }
    }
}

@Composable
private fun SearchItem(
    uiModel: SearchCurrencyUi,
    searchQuery: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Image(
            painter = painterResource(uiModel.iconRes),
            contentDescription = null,
            modifier = Modifier.size(32.dp),
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            HighlightableText(
                text = uiModel.name,
                highlight = searchQuery,
                textStyle = MaterialTheme.typography.labelLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
            )
            HighlightableText(
                text = uiModel.code.uppercase(),
                highlight = searchQuery,
                textStyle = MaterialTheme.typography.labelMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
            )
        }
        Text(
            modifier = Modifier.weight(1f),
            text = uiModel.priceUsd.formatted,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun HighlightableText(
    text: String,
    highlight: String,
    textStyle: TextStyle,
    highlightColor: Color = SearchHighlightBackground,
) {
    val annotatedString = buildAnnotatedString {
        val startIndex = text.indexOf(highlight, ignoreCase = true)
        if (startIndex == -1) {
            append(text)
        } else {
            val endIndex = startIndex + highlight.length
            append(text.take(startIndex))
            withStyle(SpanStyle(background = highlightColor)) {
                append(text.substring(startIndex, endIndex))
            }
            append(text.substring(endIndex))
        }
    }

    Text(
        text = annotatedString,
        style = textStyle,
    )
}

@PreviewLightDark
@Composable
fun SearchItemsPreview() {
    CryptomeTheme {
        Surface {
            SearchItems(
                contentPadding = PaddingValues(16.dp),
                searchQuery = "",
                items = listOf(
                    SearchCurrencyUi(
                        id = "BTC",
                        name = "Bitcoin",
                        symbol = "BTC",
                        code = "BTC",
                        priceUsd = DisplayableNumber(
                            value = BigDecimal(96046.09),
                            formatted = "$96046.09"
                        ),
                        iconRes = RUi.drawable.btc,
                    ),
                    SearchCurrencyUi(
                        id = "ETH",
                        name = "Ethereum",
                        symbol = "ETH",
                        code = "ETH",
                        priceUsd = DisplayableNumber(
                            value = BigDecimal(3168.85),
                            formatted = "$3168.85"
                        ),
                        iconRes = RUi.drawable.eth,
                    ),
                    SearchCurrencyUi(
                        id = "USDT",
                        name = "Tether",
                        symbol = "USDT",
                        code = "USDT",
                        priceUsd = DisplayableNumber(
                            value = BigDecimal(1.00),
                            formatted = "$1.00"
                        ),
                        iconRes = RUi.drawable.usdt,
                    ),
                    SearchCurrencyUi(
                        id = "BNB",
                        name = "BNB",
                        symbol = "BNB",
                        code = "BNB",
                        priceUsd = DisplayableNumber(
                            value = BigDecimal(582.14),
                            formatted = "$582.14"
                        ),
                        iconRes = RUi.drawable.bnb,
                    ),
                    SearchCurrencyUi(
                        id = "SOL",
                        name = "Solana",
                        symbol = "SOL",
                        code = "SOL",
                        priceUsd = DisplayableNumber(
                            value = BigDecimal(162.50),
                            formatted = "$162.50"
                        ),
                        iconRes = RUi.drawable.sol,
                    ),
                    SearchCurrencyUi(
                        id = "XRP",
                        name = "XRP",
                        symbol = "XRP",
                        code = "XRP",
                        priceUsd = DisplayableNumber(
                            value = BigDecimal(0.52),
                            formatted = "$0.52"
                        ),
                        iconRes = RUi.drawable.xrp,
                    ),
                    SearchCurrencyUi(
                        id = "USDC",
                        name = "USD Coin",
                        symbol = "USDC",
                        code = "USDC",
                        priceUsd = DisplayableNumber(
                            value = BigDecimal(1.00),
                            formatted = "$1.00"
                        ),
                        iconRes = RUi.drawable.usdc,
                    ),
                    SearchCurrencyUi(
                        id = "ADA",
                        name = "Cardano",
                        symbol = "ADA",
                        code = "ADA",
                        priceUsd = DisplayableNumber(
                            value = BigDecimal(0.46),
                            formatted = "$0.46"
                        ),
                        iconRes = RUi.drawable.ada,
                    ),
                    SearchCurrencyUi(
                        id = "DOGE",
                        name = "Dogecoin",
                        symbol = "DOGE",
                        code = "DOGE",
                        priceUsd = DisplayableNumber(
                            value = BigDecimal(0.085),
                            formatted = "$0.085"
                        ),
                        iconRes = RUi.drawable.doge,
                    ),
                    SearchCurrencyUi(
                        id = "AVAX",
                        name = "Avalanche",
                        symbol = "AVAX",
                        code = "AVAX",
                        priceUsd = DisplayableNumber(
                            value = BigDecimal(39.72),
                            formatted = "$39.72"
                        ),
                        iconRes = RUi.drawable.avax,
                    ),
                    SearchCurrencyUi(
                        id = "TRX",
                        name = "TRON",
                        symbol = "TRX",
                        code = "TRX",
                        priceUsd = DisplayableNumber(
                            value = BigDecimal(0.13),
                            formatted = "$0.13"
                        ),
                        iconRes = RUi.drawable.trx,
                    ),
                )
            )
        }
    }
}