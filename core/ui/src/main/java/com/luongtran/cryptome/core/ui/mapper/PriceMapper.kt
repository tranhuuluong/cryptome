package com.luongtran.cryptome.core.ui.mapper

import android.icu.number.NumberFormatter
import android.icu.number.Precision
import android.icu.number.Scale
import android.icu.util.Currency
import android.icu.util.MeasureUnit
import com.luongtran.cryptome.core.ui.model.DisplayableNumber
import java.math.BigDecimal
import java.util.Locale

fun BigDecimal.toDisplayableNumber(): DisplayableNumber {
    val formatted = NumberFormatter.withLocale(Locale.getDefault())
        .precision(Precision.minMaxFraction(2, 2))
        .unit(Currency.getInstance("USD"))
        .format(this)
        .toString()
    return DisplayableNumber(value = this, formatted = formatted)
}

fun BigDecimal.toPercentage(): DisplayableNumber {
    val formatted = NumberFormatter.withLocale(Locale.getDefault())
        .precision(Precision.minMaxFraction(2, 2))
        .unit(MeasureUnit.PERCENT)
        .scale(Scale.none())
        .sign(NumberFormatter.SignDisplay.ALWAYS)
        .format(this)
        .toString()
    return DisplayableNumber(value = this, formatted = formatted)
}