package com.luongtran.cryptome.feature.home.ui.model

data class FilterUi(
    val options: List<FilterOption>,
    val selectedOption: FilterOption,
    val showPurchasable: Boolean,
)
