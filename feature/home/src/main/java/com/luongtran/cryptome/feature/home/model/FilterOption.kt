package com.luongtran.cryptome.feature.home.model

import androidx.annotation.StringRes
import com.luongtran.cryptome.feature.home.R

enum class FilterOption(@param:StringRes val stringRes: Int) {
    All(R.string.filter_all),
    Crypto(R.string.filter_crypto),
    Fiat(R.string.filter_fiat),
}