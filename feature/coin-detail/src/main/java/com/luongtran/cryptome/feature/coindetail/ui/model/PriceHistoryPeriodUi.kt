package com.luongtran.cryptome.feature.coindetail.ui.model

import androidx.annotation.StringRes
import com.luongtran.cryptome.feature.coindetail.R

enum class PriceHistoryPeriodUi(@param:StringRes val resId: Int) {
    HOUR(R.string.period_1h),
    DAY(R.string.period_24h),
    WEEK(R.string.period_1w),
    MONTH(R.string.period_1m),
    THREE_MONTHS(R.string.period_3m),
    SIX_MONTHS(R.string.period_6m),
    YEAR(R.string.period_1y),
    ALL(R.string.period_all)
}