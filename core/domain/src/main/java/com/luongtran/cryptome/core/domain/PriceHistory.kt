package com.luongtran.cryptome.core.domain

data class PriceHistory(
    val prices: List<Double>,
    val changePercent: Double,
    val period: PriceHistoryPeriod,
)

enum class PriceHistoryPeriod {
    HOUR,
    DAY,
    WEEK,
    MONTH,
    THREE_MONTHS,
    SIX_MONTHS,
    YEAR,
    ALL
}