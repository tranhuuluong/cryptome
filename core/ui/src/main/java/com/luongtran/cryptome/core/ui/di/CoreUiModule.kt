package com.luongtran.cryptome.core.ui.di

import com.luongtran.cryptome.core.ui.utils.DefaultNumberFormatter
import com.luongtran.cryptome.core.ui.utils.NumberFormatter
import org.koin.dsl.module
import java.text.NumberFormat

val coreUiModule = module {
    single<NumberFormat>(qualifier = PriceFormatterQualifier) {
        NumberFormat.getCurrencyInstance().apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
        }
    }
    single<NumberFormat>(qualifier = PercentFormatterQualifier) {
        NumberFormat.getInstance().apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
        }
    }
    single<NumberFormat>(qualifier = CryptoAmountFormatterQualifier) {
        NumberFormat.getInstance().apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
        }
    }
    single<NumberFormatter> {
        DefaultNumberFormatter(
            priceFormatter = get(qualifier = PriceFormatterQualifier),
            percentFormatter = get(qualifier = PercentFormatterQualifier),
            cryptoAmountFormatter = get(qualifier = CryptoAmountFormatterQualifier),
        )
    }
}