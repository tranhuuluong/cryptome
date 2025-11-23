package com.luongtran.cryptome.feature.home.ui.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.luongtran.cryptome.core.ui.model.DisplayableNumber
import com.luongtran.cryptome.feature.home.ui.model.CurrencyUi
import java.math.BigDecimal
import com.luongtran.cryptome.core.ui.R as RUi

class CurrencyListPreviewParamProvider : PreviewParameterProvider<List<CurrencyUi>> {
    override val values: Sequence<List<CurrencyUi>>
        get() = sequenceOf(CurrencyListPreviewData.data)
}

internal object CurrencyListPreviewData {
    val data = listOf(
        CurrencyUi.Crypto(
            id = "BTC",
            name = "Bitcoin",
            symbol = "BTC",
            slug = "",
            price = DisplayableNumber(
                value = BigDecimal(96046.09),
                formatted = "$96046.09"
            ),
            changePercent24Hr = DisplayableNumber(
                value = 1.02,
                formatted = "+1.02%"
            ),
            iconRes = RUi.drawable.btc,
            tradable = true,
        ),
        CurrencyUi.Crypto(
            id = "ETH",
            name = "Ethereum",
            symbol = "ETH",
            slug = "",
            price = DisplayableNumber(
                value = BigDecimal(3168.85),
                formatted = "$3168.85"
            ),
            changePercent24Hr = DisplayableNumber(
                value = 0.07,
                formatted = "+0.07%"
            ),
            iconRes = RUi.drawable.eth,
            tradable = true,
        ),
        CurrencyUi.Crypto(
            id = "USDT",
            name = "Tether",
            symbol = "USDT",
            slug = "",
            price = DisplayableNumber(
                value = BigDecimal(1.00),
                formatted = "$1.00"
            ),
            changePercent24Hr = DisplayableNumber(
                value = 0.01,
                formatted = "+0.01%"
            ),
            iconRes = RUi.drawable.usdt,
            tradable = false,
        ),
        CurrencyUi.Crypto(
            id = "BNB",
            name = "BNB",
            symbol = "BNB",
            slug = "",
            price = DisplayableNumber(
                value = BigDecimal(582.14),
                formatted = "$582.14"
            ),
            changePercent24Hr = DisplayableNumber(
                value = -0.34,
                formatted = "-0.34%"
            ),
            iconRes = RUi.drawable.bnb,
            tradable = false,
        ),
        CurrencyUi.Crypto(
            id = "SOL",
            name = "Solana",
            symbol = "SOL",
            slug = "",
            price = DisplayableNumber(
                value = BigDecimal(162.50),
                formatted = "$162.50"
            ),
            changePercent24Hr = DisplayableNumber(
                value = 2.31,
                formatted = "+2.31%"
            ),
            iconRes = RUi.drawable.sol,
            tradable = false,
        ),
        CurrencyUi.Crypto(
            id = "XRP",
            name = "XRP",
            symbol = "XRP",
            slug = "",
            price = DisplayableNumber(
                value = BigDecimal(0.52),
                formatted = "$0.52"
            ),
            changePercent24Hr = DisplayableNumber(
                value = -1.12,
                formatted = "-1.12%"
            ),
            iconRes = RUi.drawable.xrp,
            tradable = true,
        ),
        CurrencyUi.Crypto(
            id = "USDC",
            name = "USD Coin",
            symbol = "USDC",
            slug = "",
            price = DisplayableNumber(
                value = BigDecimal(1.00),
                formatted = "$1.00"
            ),
            changePercent24Hr = DisplayableNumber(
                value = 0.00,
                formatted = "0.00%"
            ),
            iconRes = RUi.drawable.usdc,
            tradable = false,
        ),
        CurrencyUi.Crypto(
            id = "ADA",
            name = "Cardano",
            symbol = "ADA",
            slug = "",
            price = DisplayableNumber(
                value = BigDecimal(0.46),
                formatted = "$0.46"
            ),
            changePercent24Hr = DisplayableNumber(
                value = 1.41,
                formatted = "+1.41%"
            ),
            iconRes = RUi.drawable.ada,
            tradable = true,
        ),
        CurrencyUi.Crypto(
            id = "DOGE",
            name = "Dogecoin",
            symbol = "DOGE",
            slug = "",
            price = DisplayableNumber(
                value = BigDecimal(0.085),
                formatted = "$0.085"
            ),
            changePercent24Hr = DisplayableNumber(
                value = -0.08,
                formatted = "-0.08%"
            ),
            iconRes = RUi.drawable.doge,
            tradable = false,
        ),
        CurrencyUi.Crypto(
            id = "AVAX",
            name = "Avalanche",
            symbol = "AVAX",
            slug = "",
            price = DisplayableNumber(
                value = BigDecimal(39.72),
                formatted = "$39.72"
            ),
            changePercent24Hr = DisplayableNumber(
                value = 3.44,
                formatted = "+3.44%"
            ),
            iconRes = RUi.drawable.avax,
            tradable = true,
        ),
        CurrencyUi.Crypto(
            id = "TRX",
            name = "TRON",
            symbol = "TRX",
            slug = "",
            price = DisplayableNumber(
                value = BigDecimal(0.13),
                formatted = "$0.13"
            ),
            changePercent24Hr = DisplayableNumber(
                value = 0.55,
                formatted = "+0.55%"
            ),
            iconRes = RUi.drawable.trx,
            tradable = false,
        ),
        CurrencyUi.Fiat(
            id = "USD",
            name = "United States Dollar",
            symbol = "$",
            code = "USD",
            exchangeRateToUsd = DisplayableNumber(
                value = 1,
                formatted = "$1.00"
            ),
            iconRes = RUi.drawable.usd,
            tradable = false,
        )
    )
}