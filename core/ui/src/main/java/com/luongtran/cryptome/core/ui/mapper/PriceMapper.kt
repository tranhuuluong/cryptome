package com.luongtran.cryptome.core.ui.mapper

import android.icu.number.NumberFormatter
import android.icu.number.Precision
import android.icu.number.Scale
import android.icu.util.Currency
import android.icu.util.MeasureUnit
import com.luongtran.cryptome.core.ui.model.DisplayableNumber
import java.math.BigDecimal
import java.util.Locale

fun BigDecimal.toDisplayableNumber(
    locale: Locale = Locale.getDefault(),
    currency: Currency = Currency.getInstance("USD"),
    precision: Precision = Precision.minMaxFraction(2, 2),
): DisplayableNumber {
    val formatted = NumberFormatter.withLocale(locale)
        .precision(precision)
        .unit(currency)
        .format(this)
        .toString()
    return DisplayableNumber(value = this, formatted = formatted)
}

fun BigDecimal.toPercentage(
    locale: Locale = Locale.getDefault(),
    precision: Precision = Precision.minMaxFraction(2, 2),
    unit: MeasureUnit = MeasureUnit.PERCENT,
    scale: Scale = Scale.none(),
    signDisplay: NumberFormatter.SignDisplay = NumberFormatter.SignDisplay.ALWAYS,
): DisplayableNumber {
    val formatted = NumberFormatter.withLocale(locale)
        .precision(precision)
        .unit(unit)
        .scale(scale)
        .sign(signDisplay)
        .format(this)
        .toString()
    return DisplayableNumber(value = this, formatted = formatted)
}