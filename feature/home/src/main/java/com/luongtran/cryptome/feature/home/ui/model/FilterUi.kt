package com.luongtran.cryptome.feature.home.ui.model

import androidx.annotation.StringRes
import com.luongtran.cryptome.feature.home.R

data class FilterUi(
    val options: List<FilterOption>,
    val selectedOption: FilterOption,
    val showTradable: Boolean,
) {
    companion object {
        fun default() = FilterUi(
            options = FilterOption.entries,
            selectedOption = FilterOption.All,
            showTradable = false,
        )
    }
}

enum class FilterOption(@param:StringRes val stringRes: Int) {
    All(R.string.filter_all),
    Crypto(R.string.filter_crypto),
    Fiat(R.string.filter_fiat),
}
